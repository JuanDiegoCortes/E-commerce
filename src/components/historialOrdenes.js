document.addEventListener("DOMContentLoaded", () => {
    //Variables
    let ordenes = [];
    let cedula = 582347196 /* localStorage.getItem("usuario.cedula"); */

    //Select DOM elements
    const contenedorOrdenes = document.querySelector("#contenedor-orden");

    const url = `http://localhost:8081/Apiweb/v1/orden/visualizarOrdenes/${cedula}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            ordenes = data;
            cargarHistorialOrdenesPorCedula(ordenes);
        })
        .catch(error => {
            console.error("Error al obtener los datos:", error);
        });

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
                <button class="regresarBtn boton-detalles" id="${orden.idOrden}">Ver detalles</button>  
            </div>
            `;
            contenedorOrdenes.appendChild(div);

            const botonDetalles = div.querySelector(".boton-detalles");
            botonDetalles.addEventListener("click", function() {
                sessionStorage.setItem("ordenSeleccionada", JSON.stringify(orden));
                window.location.href = "../pages/detalleOrden.html";
            });
        });
    }

});
