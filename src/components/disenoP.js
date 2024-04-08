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

const contenerdorDisenos = document.querySelector("#contenedor-disenos");

function fetchData(){
    return fetch("http://localhost:8081/Apiweb/v1/disenoP/")
    .then(response => response.json())
    .then(data => {
        DisenosP = data;
        cargarDisenosP(Disenos);
    });
}

function cargarDisenosP(DisenosP){
    contenerdorDisenos.innerHTML = "";

    DisenosP.forEach(DisenoP => {
        const div = document.createElement("div");
        div.classList.add("disenoP");
        div.innerHTML = `
            <img class="disenoP-imagen" src="${DisenoP.image_Url}" alt="${DisenoP.nombre}">
            <div class="disenoP-detalles">
                <h3 class="disenoP-titulo">${DisenoP.nombre}</h3>
                <p class="disenoP-precio">Precio: ${DisenoP.precio}</p>
                <button class="disenoP-agregar" id="${DisenoP.idDisenoP}">Agregar</button>
                <button class="disenoP-info" id="${DisenoP.idDisenoP}">Informacion</button>
            </div>
        `;
        contenerdorDisenos.append(div);
    });
    //Toca revisar. Los atributos que vamos a mostrar ya que no entiendo muy bien en si el DisenoP
}
