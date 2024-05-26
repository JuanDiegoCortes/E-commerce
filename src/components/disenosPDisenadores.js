document.addEventListener("DOMContentLoaded", () => {

    let disenosP = [];  
    let estadoActivo = "todos";

    const contenedorDisenosP = document.querySelector("#contenedor-disenosP");
    const aside = document.querySelector("aside");
    const botonEstado = document.querySelectorAll(".boton-estado");
    const botonBuscarID = document.querySelector(".button-search-id");
    const botonBuscarNombre = document.querySelector(".button-search-nombre");
    const inputFiltro = document.querySelector(".input-search");

    const containerDiseñosP = document.querySelector(".contenedor-disenosP");
    
    const url = 'http://localhost:8081/Apiweb/v1/disenoP/';
    fetch(url) 
    .then(response => response.json())
    .then(data => {
        disenosP = data;
        mostrarDisenosP(disenosP);
    })
    .catch(error => {
        console.error("Error al obtener los datos:", error);
    });

    function mostrarDisenosP(disenoP){
        contenedorDisenosP.innerHTML = "";

        disenoP.forEach(disenoP => {
            const div = document.createElement("div");
            div.classList.add("disenoP");
            div.innerHTML = `
            <div class="disenoP-atributos">
                <p> ID disenoP:${disenoP.idDisenoP}</p>
                <p> Estado: ${disenoP.estado}</p>
                <p> Cedula del cliente: ${disenoP.cedula.cedula}</p>
                <p> Nombre del cliente: ${disenoP.cedula.nombre}</p>
            </div>
            <div class="boton-info">
                <button class="regresarBtn boton-detalles" id="${disenoP.idDisenoP}">Ver detalles</button>
            </div>
            `;
            contenedorDisenosP.appendChild(div);

            const botonDetalles = div.querySelector(".boton-detalles");
            botonDetalles.addEventListener("click", function() {
                sessionStorage.setItem("disenoPSeleccionado", JSON.stringify(disenoP));
                window.location.href = "../pages/detalleDisenoP.html";
            });
        })
        addEventListener();
    }

    function addEventListener (){
        botonEstado.forEach(boton => boton.addEventListener("click", function(e) {
            aside.classList.remove("aside-visible");
            botonEstado.forEach(boton => boton.classList.remove("active"));
            e.currentTarget.classList.add("active");
            estadoActivo = e.currentTarget.id;

        
            if (estadoActivo !== "todos") {
                const disenosEstado = disenosP.filter(disenoP => disenoP.estado === estadoActivo);
                if (disenosEstado.length === 0) {
                    containerDiseñosP.innerHTML = "No hay diseños con ese estado.";
                } else {
                    mostrarDisenosP(disenosEstado);
                }
            } else {
                mostrarDisenosP(disenosP);
            }

        }));

        botonBuscarID.addEventListener("click", function(){
            const idBuscado = parseInt(inputFiltro.value);
            if (!inputFiltro.value) {
                mostrarDisenosP(disenosP);
                return;
            }

            const disenoPFiltrado = disenosP.find(disenoP => disenoP.idDisenoP === idBuscado);
        
            if (disenoPFiltrado){
                mostrarDisenosP([disenoPFiltrado]);
            }else{
                containerDiseñosP.innerHTML = "No se encontro ningun diseño con ese ID";
            }
        });

        botonBuscarNombre.addEventListener("click", function(){
            if (!inputFiltro.value) {
                mostrarDisenosP(disenosP);
                return;
            }

            const nombreBuscado = inputFiltro.value.toLowerCase();
            let disenosParaFiltrar;

            if (estadoActivo !== "todos") {
                disenosParaFiltrar = disenosP.filter(disenoP => disenoP.estado === estadoActivo);
            } else {
                disenosParaFiltrar = disenosP;
            }
            const disenosFiltrados = disenosParaFiltrar.filter(disenoP => disenoP.cedula.nombre.toLowerCase().includes(nombreBuscado));

            if (disenosFiltrados.length > 0) {
                mostrarDisenosP(disenosFiltrados);
            } else {
                containerDiseñosP.innerHTML = "No se encontro ningun diseño con ese nombre en el estado seleccionado.";
            }
        });
    }
});






