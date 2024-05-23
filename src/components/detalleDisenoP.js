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


        const AgregarDisenosHeader = document.createElement("tr");
        AgregarDisenosHeader.innerHTML = `
            <th> Agregar diseño </th>
        `;
        table.appendChild(AgregarDisenosHeader);

        const trAgregarDisenos = document.createElement("tr");
        trAgregarDisenos.innerHTML = `
            <td><input type="text" id="input-${orden.idDisenoP}" class="inputAgregarDiseno" name="inputAgregarDiseno"> <button class = "botonAsignar" id= "${orden.idDisenoP}" type="button" name="asignarImagenButton"> Asignar </button></td>
        `;

        table.appendChild(trAgregarDisenos);
        containerOrdenes.appendChild(table);

        asignarImagenBoton();
    }

    function asignarImagenBoton(){
    const botonAsignar = document.querySelectorAll(".botonAsignar");
    botonAsignar.forEach(boton => {
        boton.addEventListener("click", function(e) {
            const idDisenoP = e.target.id;
            const inputId = 'input-' + idDisenoP;
            const imagenAsignada = document.getElementById(inputId).value;

            console.log(idDisenoP, imagenAsignada);
            asignarImagen(idDisenoP, imagenAsignada);
        });
    });
    }

    function asignarImagen(idDisenoP, imagenAsignada){
        console.log(idDisenoP, imagenAsignada);
        const urlImagen = `http://localhost:8081/Apiweb/v1/disenoP/actualizarImagen/${idDisenoP}`; 
        fetch(urlImagen, {
            method: 'PUT',
            body: imagenAsignada,
            headers:{
                'Content-Type': 'application/json'
            }
        }).then(response => response)
        .then(() => {
            Toastify({
                text: "Diseño asignado correctamente",
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
                let disenoPSeleccionado = JSON.parse(sessionStorage.getItem('disenoPSeleccionado'));
                disenoPSeleccionado.image_url = imagenAsignada;
                sessionStorage.setItem('disenoPSeleccionado', JSON.stringify(disenoPSeleccionado));
                setTimeout(() => {
                    location.reload();
                }, 1000);
            })
        .catch(error => console.error(error));
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