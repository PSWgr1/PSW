function compileFile(file) {
    var points = [];
	var cubes = [];
	//var file = file.slice(1);
	var lines = file.match(/[^\r\n]+/g)
	var section = "";
	lines.forEach(function (item) {
		if (item[0] == "*") {
			section = item.slice(1).trim();

		} else
			switch (section) {
			case "NODE":
				var itemElems = item.split(",");
				points[itemElems[0]] = {
					x: itemElems[1],
					y: itemElems[2],
					z: itemElems[3],
					temp: itemElems[4]
				}
				break;
			case "ELEMENT_SOLID":
				var cubeElements = item.split(",");
				cubes[cubeElements[0]] = {
					PartId: cubeElements[1].trim(),
					Points: [
						points[cubeElements[2].trim()],
						points[cubeElements[3].trim()],
						points[cubeElements[4].trim()],
						points[cubeElements[5].trim()],
						points[cubeElements[6].trim()],
						points[cubeElements[7].trim()],
						points[cubeElements[8].trim()],
						points[cubeElements[9].trim()],

					]
				}

			}

	})

	return {
		Points: points,
		Cubes: cubes
	}

}
var file;


function avarageOfExtrimes(arr) {
	var min = Math.min.apply(null, arr);
	var max = Math.max.apply(null, arr);

	return (max + min) / 2;
}
	var triangles = [];
	var temps = [];
function pushTriangle(item,a,b,c){
		triangles.push(item.Points[a].x*10);
		triangles.push(item.Points[a].y*10);
		triangles.push(item.Points[a].z*10);
		temps.push(item.Points[a].temp);

		triangles.push(item.Points[b].x*10);
		triangles.push(item.Points[b].y*10);
		triangles.push(item.Points[b].z*10);
		temps.push(item.Points[b].temp);

		triangles.push(item.Points[c].x*10);
		triangles.push(item.Points[c].y*10);
		triangles.push(item.Points[c].z*10);
		temps.push(item.Points[c].temp);
}

function getCubes(){
   
	var ddd = compileFile(file) 
	
	return   ddd.Cubes;
}

function getPoints(){
   
	var ddd = compileFile(file) 
	
	return   ddd.Points;
}

function createGeometry(fileData) {
    file = fileData;
	var cubes = getCubes();
	var geometry = new THREE.BufferGeometry();
	 triangles = [];
	 temps = [];

	cubes.forEach(function (item) {
		
		
		pushTriangle(item,0,1,2);
		pushTriangle(item,0,2,3);
		
		pushTriangle(item,0,3,4);
		pushTriangle(item,3,4,7);
		
		pushTriangle(item,2,3,6);
		pushTriangle(item,3,6,7);
		
		pushTriangle(item,0,1,4);
		pushTriangle(item,1,4,5);
		
		pushTriangle(item,4,5,6);
		pushTriangle(item,6,4,7);
		
		pushTriangle(item,1,2,6);
		pushTriangle(item,1,5,6);
		
		item.Points.forEach(function (point) {
		    geometry.minX = Math.min(geometry.minX || 0, point.x);
		    geometry.minY = Math.min(geometry.minY || 0, point.y);
		    geometry.minZ = Math.min(geometry.minZ || 0, point.z);
		    geometry.maxX = Math.max(geometry.maxX || 0, point.x);
		    geometry.maxY = Math.max(geometry.maxY || 0, point.y);
		    geometry.maxZ = Math.max(geometry.maxZ || 0, point.z);


		})
	})

	var vertices = new Float32Array(triangles);

	var pressure = new Float32Array(temps);

	// itemSize = 3 because there are 3 values (components) per vertex
	geometry.addAttribute('position', new THREE.BufferAttribute(vertices, 3));
	geometry.addAttribute('pressure', new THREE.BufferAttribute(pressure, 3));
	return geometry
}
