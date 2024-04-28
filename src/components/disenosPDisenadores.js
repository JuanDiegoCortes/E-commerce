document.addEventListener("DOMContentLoaded", () => {

    let disenosP = [];  

    const contenedorDisenosP = document.querySelector("#contenedor-disenosP");

    const url = 'http://localhost:8081/Apiweb/v1/disenoP/';
    fetch(url) 
    .then(response => response.json())
    .then(data => {
        disenosP = data;
        console.log(disenosP);
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
                <p> ID:${disenoP.idDisenoP}</p>
                <p> Nombre: ${disenoP.cedula.nombre}</p>
                <p> Nombre: ${disenoP.cedula.correo}</p>
                <p> Nombre: ${disenoP.cedula.cedula}</p>
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
        
    }
});






