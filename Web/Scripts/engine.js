(function () {
	readTextFile('file.txt')
	var items = compileFile(file);

	if (!Detector.webgl) {
		Detector.addGetWebGLMessage();
	}
	var container, stats;
var camera, scene, renderer, lut, legendLayout;
var position;
var mesh;
var colorMap;
var numberOfColors;
init();
animate();
function init() {
	container = document.getElementById('container');
	// SCENE
	scene = new THREE.Scene();
	// CAMERA
	camera = new THREE.PerspectiveCamera(20, window.innerWidth / window.innerHeight, 1, 10000);
	camera.name = 'camera';
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
	loadModel(items.Cubes,colorMap, numberOfColors, legendLayout);
	camera.position.x = 17;
	camera.position.y = 9;
	camera.position.z = 32;
	var directionalLight = new THREE.DirectionalLight(0xffffff, 0.7);
	directionalLight.position.x = 17;
	directionalLight.position.y = 9;
	directionalLight.position.z = 30;
	directionalLight.name = 'directionalLight';
	scene.add(directionalLight);
	renderer = new THREE.WebGLRenderer({
			antialias: true
		});
	renderer.setClearColor(0xffffff);
	renderer.setPixelRatio(window.devicePixelRatio);
	renderer.setSize(window.innerWidth, window.innerHeight);
	container.appendChild(renderer.domElement);
	window.addEventListener('resize', onWindowResize, false);
	window.addEventListener("keydown", onKeyDown, true);
}
var rotWorldMatrix;
function rotateAroundWorldAxis(object, axis, radians) {
	if (!axis)
		return;
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
function loadModel(cubes,colorMap, numberOfColors, legendLayout) {

	var geometry = createGeometry(cubes)

	geometry.computeVertexNormals();
	geometry.normalizeNormals();
	var material = new THREE.MeshLambertMaterial({
			side: THREE.DoubleSide,
			color: 0xF5F5F5,
			vertexColors: THREE.VertexColors
		});
	var lutColors = [];
	lut = new THREE.Lut(colorMap, numberOfColors);
	lut.setMax(2000);
	lut.setMin(0);
	for (var i = 0; i < geometry.attributes.pressure.array.length; i++) {
		var colorValue = geometry.attributes.pressure.array[i];
		color = lut.getColor(colorValue);
		if (color == undefined) {
			console.log("ERROR: " + colorValue);
		} else {
			lutColors[3 * i] = color.r;
			lutColors[3 * i + 1] = color.g;
			lutColors[3 * i + 2] = color.b;
		}
	}
	geometry.addAttribute('color', new THREE.BufferAttribute(new Float32Array(lutColors), 3));
	mesh = new THREE.Mesh(geometry, material);
	geometry.computeBoundingBox();
	var boundingBox = geometry.boundingBox;
	var center = boundingBox.getCenter();
	if (position === undefined) {
		position = new THREE.Vector3(center.x, center.y, center.z);
	}
	scene.add(mesh);
	if (legendLayout) {
		if (legendLayout == 'horizontal') {
			legend = lut.setLegendOn({
					'layout': 'horizontal',
					'position': {
						'x': 21,
						'y': 6,
						'z': 5
					}
				});
		} else {
			legend = lut.setLegendOn();
		}
		scene.add(legend);
		var labels = lut.setLegendLabels({
				'title': 'Pressure',
				'um': 'Pa',
				'ticks': 5
			});
		scene.add(labels['title']);
		for (var i = 0; i < Object.keys(labels['ticks']).length; i++) {
			scene.add(labels['ticks'][i]);
			scene.add(labels['lines'][i]);
		}
	}

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
function onKeyDown(e) {
	var maps = ['rainbow', 'cooltowarm', 'blackbody', 'grayscale'];
	var colorNumbers = ['16', '128', '256', '512'];
	if (e.keyCode == 65) {
		cleanScene();
		var index = maps.indexOf(colorMap) >= maps.length - 1 ? 0 : maps.indexOf(colorMap) + 1;
		colorMap = maps[index];
		loadModel(items.Cubes,colorMap, numberOfColors, legendLayout);
	} else if (e.keyCode == 83) {
		cleanScene();
		var index = colorNumbers.indexOf(numberOfColors) >= colorNumbers.length - 1 ? 0 : colorNumbers.indexOf(numberOfColors) + 1;
		numberOfColors = colorNumbers[index];
		loadModel(items.Cubes,colorMap, numberOfColors, legendLayout);
	} else if (e.keyCode == 68) {
		if (!legendLayout) {
			cleanScene();
			legendLayout = 'vertical';
			loadModel(items.Cubes,colorMap, numberOfColors, legendLayout);
		} else {
			cleanScene();
			legendLayout = lut.setLegendOff();
			loadModel(items.Cubes,colorMap, numberOfColors, legendLayout);
		}
	} else if (e.keyCode == 70) {
		cleanScene();
		if (!legendLayout)
			return false;
		lut.setLegendOff();
		if (legendLayout == 'horizontal') {
			legendLayout = 'vertical';
		} else {
			legendLayout = 'horizontal';
		}
		loadModel(items.Cubes,colorMap, numberOfColors, legendLayout);
	}
}
})();
