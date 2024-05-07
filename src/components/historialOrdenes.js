document.addEventListener("DOMContentLoaded", () => {
    const usuario = JSON.parse(localStorage.getItem("usuario"));
    
    //Variables
    let ordenes = [];
    let cedula = /* usuario.cedula.cedula;  */582347196

    //Select DOM elements
    const contenedorOrdenes = document.querySelector("#contenedor-orden");
    const aside = document.querySelector("aside");

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

    function addEventListeners() {  
        aside.classList.remove("aside-visible");
        let btnFiltro = document.querySelectorAll('.boton-estado');
        btnFiltro.forEach(boton => {
            boton.addEventListener("click", function(e) {
                btnFiltro.forEach(boton => boton.classList.remove("active"));
                e.currentTarget.classList.add("active");
                estadoActivo = e.currentTarget.id;
        
                if (estadoActivo !== "todos") { 
                    const ordenesEstado = ordenes.filter(orden => orden.estado === estadoActivo);
                    if (ordenesEstado.length === 0) {
                        contenedorOrdenes.innerHTML = "No hay órdenes con este estado.";
                    } else {
                        cargarHistorialOrdenesPorCedula(ordenesEstado);
                    }
                } else {
                    cargarHistorialOrdenesPorCedula(ordenes);
                }
            });
        });
    }
    addEventListeners();

});
