(function () {// Define the `phonecatApp` module
    var phonecatApp = angular.module('phonecatApp', ["ngTable"]);

    phonecatApp.directive("fileread", [function () {
        return {
            scope: {
                fileread: "="
            },
            link: function (scope, element, attributes) {
                element.bind("change", function (changeEvent) {
                    var reader = new FileReader();
                    reader.onload = function (loadEvent) {
                        scope.$apply(function () {
                            scope.fileread = loadEvent.target.result;
                        });
                    }
                    reader.readAsDataURL(changeEvent.target.files[0]);
                });
            }
        }
    }]);
    // Define the `PhoneListController` controller on the `phonecatApp` module
    phonecatApp.controller('PhoneListController', function PhoneListController($scope, $http,NgTableParams) {
        $scope.Files = [];
        $scope.tableParams = {};
        $scope.ViewType = "content";
        $scope.Rotation = true;
        $scope.CurrentFile =
            {
                Name: "",
                Text: "",
                Accessibility: "",
                Description: ""
            };
       
        $http({
            method: 'GET',
            url: 'api/files'
        }).then(function successCallback(response) {
            $scope.Files = response.data;
            $scope.tableParams = new NgTableParams({}, { dataset: $scope.Files });
        }, function errorCallback(response) { });

      
        
        $scope.geometry = {};
        $scope.RawFile = {};
        $scope.Download = function()
        {
            saveFile($scope.CurrentFile.Name, 'text/plain', $scope.CurrentFile.Text)
        }
  

        function saveFile(name, type, data) {
            if (data != null && navigator.msSaveBlob)
                return navigator.msSaveBlob(new Blob([data], { type: type }), name);
            var a = $("<a style='display: none;'/>");
            var url = window.URL.createObjectURL(new Blob([data], { type: type }));
            a.attr("href", url);
            a.attr("download", name);
            $("download").append(a);
            a[0].click();
            window.URL.revokeObjectURL(url);
            a.remove();
        }
        $scope.ChangeColormap = function () {

            var maps = ['rainbow', 'cooltowarm', 'blackbody', 'grayscale'];
            var colorNumbers = ['16', '128', '256', '512'];

            cleanScene();
            var index = maps.indexOf(colorMap) >= maps.length - 1 ? 0 : maps.indexOf(colorMap) + 1;
            colorMap = maps[index];
            loadModel(colorMap, numberOfColors, legendLayout);


        }

        $scope.LoadFile = function (id) {
            if (Number(parseFloat(id)) == id) {
                $http({
                    method: 'GET',
                    url: 'api/files/' + id
                }).then(function successCallback(response) {
                    $scope.CurrentFile = response.data;
                    loadModel(colorMap, numberOfColors, legendLayout)
                }, function errorCallback(response) { })
            } else {
                if (FileReader) {
                    $scope.RawFile = document.getElementById('fileItem').files[0];
                    $scope.CurrentFile.Text = getBase64($scope.RawFile);
                    $scope.CurrentFile.Name = $scope.RawFile.name
                    $scope.$apply();
                    loadModel(colorMap, numberOfColors, legendLayout)
                }
            }
            $scope.ViewType = 'graph';
        }

        document.getElementById('fileItem').onchange = $scope.LoadFile;

        function getBase64(file) {
            var reader = new FileReader();
            reader.readAsText(file);
            reader.onload = function (theFile) {
                $scope.CurrentFile.Text = theFile.target.result;
                loadModel(colorMap, numberOfColors, legendLayout)
            };
            reader.onerror = function (error) {
                $scope.CurrentFile.Text = error;
            };
        }
        $scope.AddCurrentFile = function () {
            $http({
                method: 'POST',
                url: "/api/files",
                data: $scope.CurrentFile
            }).then(function successCallback(response) {
                var fff = 0;
            }, function errorCallback(response) {
                var fff = 0;
            });
        }
        $scope.CopyCurrentFile = function ()
        {
            $scope.CurrentFile.Id = 0;
            $scope.AddCurrentFile();
        }
        if (!Detector.webgl) { Detector.addGetWebGLMessage(); }
        var container, stats;
        var camera, scene, renderer, lut, legendLayout;
        var position;
        var mesh;
        var colorMap;
        var numberOfColors;
        init();
        animate();
        function init() {
            container = document.getElementById('displayWindow');
            // SCENE
            scene = new THREE.Scene();
            // CAMERA
            camera = new THREE.PerspectiveCamera(50, window.innerWidth / window.innerHeight, 0.001, 10000);
            camera.name = 'camera';
            controls = new THREE.OrbitControls(camera, container);
            controls.maxPolarAngle = 0.9 * Math.PI / 2;
            controls.enableZoom = true;
            scene.add(camera);
            stats = new Stats();
            container.appendChild(stats.dom);
            // LIGHT
            var ambientLight = new THREE.AmbientLight(0x444444);
            ambientLight.name = 'ambientLight';
            scene.add(ambientLight);
            colorMap = 'rainbow';
            numberOfColors = 512;
            legendLayout = 'vertical';
            loadModel(colorMap, numberOfColors, legendLayout);
            camera.position.x = 1;
            camera.position.y = 1;
            camera.position.z = 1;
            var directionalLight = new THREE.DirectionalLight(0xffffff, 0.7);
            directionalLight.position.x = camera.position.x;
            directionalLight.position.y = camera.position.y;
            directionalLight.position.z = camera.position.z;
            directionalLight.name = 'directionalLight';
            scene.add(directionalLight);
            renderer = new THREE.WebGLRenderer({ antialias: true });
            renderer.setClearColor(0xffffff);
            renderer.setPixelRatio(window.devicePixelRatio);
            renderer.setSize(container.clientWidth, window.innerHeight * container.clientWidth / window.innerWidth);
            container.appendChild(renderer.domElement);

        }
        var rotWorldMatrix;
        function rotateAroundWorldAxis(object, axis, radians) {
            if (!axis) return;
            if (!$scope.Rotation) return;
            rotWorldMatrix = new THREE.Matrix4();
            rotWorldMatrix.makeRotationAxis(axis.normalize(), radians);
            rotWorldMatrix.multiply(object.matrix);
            object.matrix = rotWorldMatrix;
            object.rotation.setFromRotationMatrix(object.matrix);
        }
        function onWindowResize() {
            camera.aspect = window.innerWidth / window.innerHeight;
            camera.updateProjectionMatrix();
            renderer.setSize(window.innerWidth, window.innerHeight);
            render();
        }
        function animate() {
            requestAnimationFrame(animate);
            render();
            stats.update();
        }
        function render() {
            rotateAroundWorldAxis(mesh, position, Math.PI / 180);
            renderer.render(scene, camera);
        }
        function loadModel(colorMap, numberOfColors, legendLayout) {
            if (typeof ($scope.CurrentFile.Text) !== "string") return;
            var loader = new THREE.BufferGeometryLoader();

            loader.load("/Content/pressure.json", function () {
                if (scene.children.length > 3) {
                    scene.children.pop();
                }
                $scope.geometry = createGeometry($scope.CurrentFile.Text);
                $scope.geometry.computeVertexNormals();
                $scope.geometry.normalizeNormals();
                var ratiol = 10;
                controls.target.set(($scope.geometry.minX + $scope.geometry.maxX) / 2 * ratiol,
                    ($scope.geometry.minY + $scope.geometry.maxY) / 2 * ratiol,
                    ($scope.geometry.minZ + $scope.geometry.maxZ) / 2 * ratiol);
                var material = new THREE.MeshLambertMaterial({
                    side: THREE.DoubleSide,
                    color: 0xF5F5F5,
                    vertexColors: THREE.VertexColors
                });
                var lutColors = [];
                lut = new THREE.Lut(colorMap, numberOfColors);

                lut.setMax(Math.max.apply(null, $scope.geometry.attributes.pressure.array));
                lut.setMin(Math.min.apply(null, $scope.geometry.attributes.pressure.array));
                for (var i = 0; i < $scope.geometry.attributes.pressure.array.length; i++) {
                    var colorValue = $scope.geometry.attributes.pressure.array[i];
                    color = lut.getColor(colorValue);
                    if (color == undefined) {
                        console.log("ERROR: " + colorValue);
                    } else {
                        lutColors[3 * i] = color.r;
                        lutColors[3 * i + 1] = color.g;
                        lutColors[3 * i + 2] = color.b;
                    }
                }
                $scope.geometry.addAttribute('color', new THREE.BufferAttribute(new Float32Array(lutColors), 3));
                mesh = new THREE.Mesh($scope.geometry, material);
                $scope.geometry.computeBoundingBox();
                var boundingBox = $scope.geometry.boundingBox;
                var center = boundingBox.getCenter();
                if (position === undefined) {
                    position = new THREE.Vector3(center.x, center.y, center.z);
                }
                scene.add(mesh);
                var worldDir = camera.getWorldDirection();
                var zoom = camera.zoom;
                if (legendLayout) {
                    var pos = {
                        x: worldDir.x / 5,
                        y: worldDir.y,
                        z: worldDir.z / 5,
                    }

                    legend = lut.setLegendOn({ 'layout': 'vertical', 'position': pos });

                    while (camera.children.length > 1) {
                        camera.children.pop();
                    }
                    camera.add(legend);
                    legend.position.set(7, 0, -10)
                    var labels = lut.setLegendLabels({ 'title': 'Temperature', 'um': 'C', 'ticks': 5 });
                    camera.add(labels['title']);
                    labels['title'].position.set(7.75, 1.5, -10)
                    var n = Object.keys(labels['ticks']).length;
                    for (var i = 0; i < n; i++) {
                        camera.add(labels['ticks'][i]);
                        labels['ticks'][i].position.set(8.5, -1.85 + i / n * 3.7, -10);

                    }
                }
            });
        }
        function cleanScene() {
            var elementsInTheScene = scene.children.length;
            for (var i = elementsInTheScene - 1; i > 0; i--) {
                if (scene.children[i].name != 'camera' &&
                     scene.children[i].name != 'ambientLight' &&
                     scene.children[i].name != 'directionalLight') {
                    scene.remove(scene.children[i]);
                }
            }
        }



    }
    );
})();