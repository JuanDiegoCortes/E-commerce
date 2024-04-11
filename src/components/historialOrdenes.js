let ordenes = [];
//Select DOM elements
const contenedorOrdenes = document.querySelector("#contenedor-orden");

cedula = 582347196 /* localStorage.getItem("cedula"); */
url = `http://localhost:8081/Apiweb/v1/orden/visualizarOrdenes/`;

function fetchData() {
    return fetch(url + cedula)
        .then(response => response.json())
        .then(data => {
            ordenes = data;
            cargarHistorialOrdenesPorCedula(ordenes);
            console.log(ordenes);
        })
        .catch(error => {
            console.error("Error al obtener los datos:", error);
        });
}

function cargarHistorialOrdenesPorCedula(ordenesSeleccionadas) {
    contenedorOrdenes.innerHTML = "";

    ordenesSeleccionadas.forEach(orden => {
        const div = document.createElement("div");
        div.classList.add("orden");
        div.innerHTML = `
        <div class="historial-Orden-atributos">
            <p>${orden.cedula.nombre}</p>
            <p>${orden.cedula.correo}</p>
            <p>${orden.fecha}</p>
            <p>${orden.estado}</p>
            <p>${orden.precioTotal}</p>
        </div>
        <div class="boton-info">
            <button class="regresarBtn"> Info </button>
        </div>
    `;
        contenedorOrdenes.appendChild(div);
    });
}
fetchData();