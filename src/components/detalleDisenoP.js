window.onload = function() {
    const disenoPSeleccionado = JSON.parse(sessionStorage.getItem("disenoPSeleccionado"));

    const contenedorComentarios = document.getElementById('comentarios');
    const botonEnviar = document.getElementById('btn-enviar-comentario');
    
    function mostrarDisenoP(orden) {
        const containerOrdenes = document.querySelector(".contenedor-informacion");
        containerOrdenes.innerHTML = "";
    
        const table = document.createElement("table");
    
        const trHeader = document.createElement("tr");
        trHeader.innerHTML = `
            <th>Nombre</th>
            <th>Correo</th>
            <th>Cédula</th>
            <th>Estado orden</th>
            <th>Imagen Producto</th>
            <th>Imagen Diseño</th>
        `;
        table.appendChild(trHeader);
    
        const tr = document.createElement("tr");
        tr.innerHTML = `
        <td>${orden.cedula.nombre}</td> 
        <td>${orden.cedula.correo}</td>
        <td>${orden.cedula.cedula}</td> 
        <td>${orden.idOrdenProd.idOrden.estado}</td>
        <td><img style="width: 100px; height: auto;" src="${orden.idOrdenProd.idProducto.image_Url}" alt="Imagen del producto"></td>
        <td><img style="width: 100px; height: auto;" src="${orden.image_url}" alt="Imagen de diseño"></td>
        `;
    
        table.appendChild(tr);
        containerOrdenes.appendChild(table);
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
            setTimeout(() => {
                location.reload();
                return response.json();
            }, 2000);
        })
        .then(data => {
            console.log('Success: ', data);
        })
        .catch(error => console.error('Error:', error));
    }

    function addEventListeners() {
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
    }

    mostrarDisenoP(disenoPSeleccionado)
    obtenerComentarios(disenoPSeleccionado)
    addEventListeners()
}