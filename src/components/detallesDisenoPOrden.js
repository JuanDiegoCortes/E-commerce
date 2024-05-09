window.onload = function() {
    let infoFinal = [];

    const ordenProdSeleccionada = JSON.parse(sessionStorage.getItem("ordenProdSeleccionada"));
    const idOrdenProd = ordenProdSeleccionada.idOrdenProd;
    const contenedorDisenosP = document.getElementById('container-disenop');
    const contenedorComentarios = document.getElementById('comentarios');
    const botonEnviar = document.getElementById('btn-enviar-comentario');

    const url = `http://localhost:8081/Apiweb/v1/disenoP/visualizarDisenoP/${idOrdenProd}`
    fetch(url)
    .then(response => response.json())
    .then(data => {
        let disenoP = data;
        mostrarDiseno(disenoP);
    })
    .catch(error => console.error(error));

    function mostrarDiseno(data){
        contenedorDisenosP.innerHTML = "";
    
        const table = document.createElement("table");
    
        const trHeader = document.createElement("tr");
        trHeader.innerHTML = `
            <th>Imagen Diseño</th>
            <th>Estado</th>
            <th>Editar Estado</th>
        `;
        table.appendChild(trHeader);
    
        const tr = document.createElement("tr");
        tr.innerHTML = `
        <td><img style="width: 100px; height: auto;" src="${data.image_url}" alt="Imagen de diseño"></td>
        <td>${data.estado}</td>
        <td>
            <div class="dropdown">
                <button id="${data.idOrdenProd}" class="btn btn-primary dropdown-toggle boton-editar-estado-diseno" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Estado del diseño
                </button>
                <ul class="dropdown-menu">
                    <li><button id="pendiente" class="dropdown-item boton-estado-disenoP" type="button">Pendiente</button></li>
                    <li><button id="rechazado" class="dropdown-item boton-estado-disenoP" type="button">Rechazado</button></li>
                    <li><button id="aprobado" class="dropdown-item boton-estado-disenoP" type="button">Aprobado</button></li>
                </ul>
            </div>
        </td>
        `;
    
        table.appendChild(tr);
        contenedorDisenosP.appendChild(table);
        obtenerComentarios(data);
        addEventListeners();
    }

    function obtenerComentarios(data){
        const idDisenoP = data.idDisenoP;
        const url = `http://localhost:8081/Apiweb/v1/comentario/visualizarComentarios/${idDisenoP}`
        fetch(url)
        .then(response => response.json())
        .then(data => {
            let comentarios = data; 
            mostrarComentarios(comentarios);
        })
        .catch(error => console.error(error));
    }

    function mostrarComentarios(data){
        data.forEach(comentario => {
            const div = document.createElement("div");
            div.classList.add("comentario");
            div.innerHTML = `
                <p class="comentario-usuario-texto">${comentario.cedula.nombre}: ${comentario.texto}</p>
                `;
            contenedorComentarios.append(div);
            infoFinal = comentario;
        });
    }

    function crearComentario(data){
        const url = `http://localhost:8081/Apiweb/v1/comentario/`
        fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers:{
                'Content-Type': 'application/json'
            }
        })
        .then(async response => {
            if (!response.ok) {
                const text = await response.text();
                throw new Error(text);
            }
            window.location.reload();
            return response.json();
        })
        .then(data => {
            console.log('Success: ', data);
        })
        .catch(error => console.error('Error:', error));
    }

    function actualizareEstadoDisenoP(estado) {
        console.log(estado, idOrdenProd);
        fetch(`http://localhost:8081/Apiweb/v1/disenoP/cambiarEstadoDisenoP/${idOrdenProd}/${estado}`, {
                    method: 'PUT',
                })
                .then(() => {
                    Toastify({
                        text: "Estado actualizado correctamente",
                        duration: 1000,
                        close: true,
                        gravity: "top",
                        position: "right",
                        stopOnFocus: true,
                        style: {
                            background: "linear-gradient(to right, #4b33a8, #785ce9)",
                            borderRadius: "2rem",
                        }
                    }).showToast();
                    setTimeout(() => {
                        location.reload();
                    }, 2000);
                })
                .catch(error => console.error(error));
    }

    function addEventListeners() {
        const botonesEstadoDisenoP = document.querySelectorAll('.boton-estado-disenoP');
        botonesEstadoDisenoP.forEach(botonEstadoDisenoP => {
            botonEstadoDisenoP.addEventListener('click', (e) => {
                    const estado = e.target.id;
                    actualizareEstadoDisenoP(estado);
                });
        });

        botonEnviar.addEventListener("click", () => {
            let textoComentario = document.getElementById('text-area-comentario');
            let cedulaUsuario = JSON.parse(localStorage.getItem("usuario")).cedula.cedula;
            let subIdComentarioUsuario = infoFinal.idComentario;
            let idDisenoPUsario = infoFinal.idDisenoP.idDisenoP;
            
            let comentario = {
                texto: textoComentario.value,
                cedula: { cedula: cedulaUsuario },
                subComentarios: { subIdComentario: subIdComentarioUsuario },
                idDisenoP: { idDisenoP: idDisenoPUsario }
            } 
            crearComentario(comentario);
        });
    };
}