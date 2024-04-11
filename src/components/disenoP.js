// const designs = [
//     { id: 1, name: 'XXXX', price: 500000, image: '/src/assets/img/...' },
//     { id: 2, name: 'XXXX', price: 1000000, image: '/src/assets/img/...' },
//     // ...otros diseños
// ];

// function listarDiseños() {
//     const designList = document.getElementById('design-list'); 
//     designs.forEach(design => {
//         const listItem = document.createElement('li');
//         listItem.innerHTML = `
//             <img src="${'/src/assets/img/disenoP/01.jpg'}" alt="${design.name}">
//             <h3>${design.name}</h3>
//             <p>Precio: $${design.price.toFixed(2)}</p>
//         `;
//         designList.appendChild(listItem);
//     });
// }

// window.addEventListener('load', listarDiseños);

// function buscarDiseños(){
//     const designList = document.getElementById('design-list');

//     // Agregar un mensaje de carga mientras se obtienen los diseños
//     designList.innerHTML = '<p>Cargando diseños...</p>';
    
//     fetch("http://localhost:8081/Apiweb/v1/diseñoP/")
//         .then(response => {
//             if (!response.ok) {
//                 // Si la respuesta no es exitosa, lanzar un error
//                 throw new Error('No se pudo obtener los diseños');
//             }
//             return response.json(); // Parsea la respuesta como JSON
//         })
//         .then(data => {
//             if (data.length === 0) {
//                 // Si no hay diseños, mostrar un mensaje adecuado al usuario
//                 designList.innerHTML = '<p>No se encontraron diseños disponibles.</p>';
//             } else {
//                 // Iterar sobre los datos obtenidos y agregarlos a la lista
//                 designList.innerHTML = ''; // Limpiar el mensaje de carga
//                 data.forEach(design => {
//                     const listItem = document.createElement('li');
//                     listItem.innerHTML = `
//                         <img src="${design.image_Url}" alt="${design.nombre}">
//                         <h3>${design.nombre}</h3>
//                         <p>Precio: $${design.precio.toFixed(2)}</p>`;
//                     designList.appendChild(listItem);
//                 });
//             }
//         })
//         .catch(error => {
//             // Manejar cualquier error que ocurra durante la solicitud
//             console.error('Error al obtener los diseños:', error);
//             // Mostrar un mensaje de error al usuario
//             designList.innerHTML = '<p>Ocurrió un error al cargar los diseños. Por favor, inténtalo de nuevo más tarde.</p>';
//         });
// }

// // Llama a la función buscarDiseños para cargar los diseños cuando se cargue la página
// window.addEventListener('load', buscarDiseños);


//const contenerdorDisenos = document.querySelector("#contenedor-disenos");
let DisenosP = [];

function fetchData(){
    return fetch("http://localhost:8081/Apiweb/v1/disenoP/")
    .then(response => response.json())
    .then(data => {
        DisenosP = data;
        console.log(DisenosP);
        //cargarDisenosP(DisenosP);
    });
}

fetchData(DisenosP);

// se daño el metodo de listarDisenosP en postman


// function cargarDisenosP(disenosElegidos){
//     contenerdorDisenos.innerHTML = "";

//     disenosElegidos.forEach(DisenoP => {
//         const div = document.createElement("div");
//         div.classList.add("disenoP");
//         div.innerHTML = `
//             <img class="disenoP-imagen" src="${DisenoP.image_Url}" alt="${DisenoP.nombre}">
//             <div class="disenoP-detalles">
//                 <h3 class="disenoP-titulo">${DisenoP.nombre}</h3>
//                 <p class="disenoP-precio">Precio: ${DisenoP.precio}</p>
//                 <button class="disenoP-agregar" id="${DisenoP.idDisenoP}">Agregar</button>
//                 <button class="disenoP-info" id="${DisenoP.idDisenoP}">Informacion</button>
//             </div>
//         `;
//         contenerdorDisenos.append(div);
//     });
//     //Toca revisar. Los atributos que vamos a mostrar ya que no entiendo muy bien en si el DisenoP
// }
