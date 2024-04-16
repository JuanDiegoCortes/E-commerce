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
        const div = document.createElement("div");
        div.classList.add("disenoP");
        div.innerHTML = `
            <img class="disenoP-imagen" src="${data.image_url}" alt="DisenoP">
            `;
        contenedorDisenosP.append(div);
        obtenerComentarios(data);
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

    botonEnviar.addEventListener("click", () => {
        let textoComentario = document.getElementById('text-area-comentario');
        let cedulaUsuario = JSON.parse(localStorage.getItem("usuario")).cedula.cedula;
        let subIdComentarioUsuario = infoFinal.idComentario;
        let idDisenoPUsario = infoFinal.idDisenoP.idDisenoP;
        
        let comentario = {
            texto: textoComentario.value,
            cedula: { cedula: cedulaUsuario },
            subIdComentario: { subIdComentario: subIdComentarioUsuario },
            idDisenoP: { idDisenoP: idDisenoPUsario }
        } 
        crearComentario(comentario);
    });

    function crearComentario(comentario){
        console.log("Enviando comentario: ", comentario);
        const url = `http://localhost:8081/Apiweb/v1/comentario/`
        fetch(url, {
            method: 'POST',
            body: JSON.stringify(comentario),
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
}