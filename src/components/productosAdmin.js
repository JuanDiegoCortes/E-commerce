document.addEventListener('DOMContentLoaded', () => {

    let generoSeleccionado = "";
    let estadoSeleccionado = "";
    let categoriaSeleccionadaId = "";
    let subCategoriaSeleccionadaId = "";
    let tallasSeleccionadas = {};
    ['XS', 'S', 'M', 'L', 'XL'].forEach(talla => {
        document.getElementById(`talla${talla}`).addEventListener('change', function() {
            if (this.checked) {
                let cantidad = document.getElementById(`cantidad${talla}`).value;
                tallasSeleccionadas[talla] = cantidad;
            } else {
                delete tallasSeleccionadas[talla];
            }
        });
    });

    document.querySelectorAll('.boton-genero').forEach(button => {
        button.addEventListener('click', function() {
            generoSeleccionado = this.id;
        });
    });

    document.querySelectorAll('.boton-estado').forEach(button => {
        button.addEventListener('click', function() {
            estadoSeleccionado = this.id;
        });
    });

    document.querySelectorAll('.boton-categoria').forEach(button => {
        button.addEventListener('click', function() {
            categoriaSeleccionadaId = parseInt(this.id);
        });
    });

    document.querySelectorAll('.boton-subCategoria').forEach(button => {
        button.addEventListener('click', function() {
            subCategoriaSeleccionadaId = parseInt(this.id);
        });
    });

    document.getElementById('image_Url').addEventListener('change', function() {
        imagenSeleccionada = this.files[0];
    });


    let botonCrear = document.getElementById("crearProductoBtn");
    botonCrear.addEventListener("click", capturarValores);
    
    // Función para capturar valores y enviar la solicitud
    function capturarValores() {
        // Obtener valores del formulario
        let nombre = document.getElementById('nombre').value;
        let descripcion = document.getElementById('descripcion').value;
        let precio = parseFloat(document.getElementById('precio').value);

        // Validar campos requeridos
        if (nombre === "" || descripcion === "" || precio === "" || generoSeleccionado === "" || estadoSeleccionado === "" || categoriaSeleccionada === "" || subCategoriaSeleccionada === "") {
            alert('Por favor, complete todos los campos.');
            return "";
        }else{
            const productoData = {
                nombre: nombre,
                descripcion: descripcion,
                precio: precio,
                genero: generoSeleccionado,
                estado: estadoSeleccionado,
                idCategoria: {
                    idCategoria: categoriaSeleccionadaId
                },
                subIdCategoria:{
                    idSubCategoria: subCategoriaSeleccionadaId
                },
                image_Url: imagenSeleccionada,
                idtallas: {
                    idtallas: tallasSeleccionadas
                } 
            };
            console.log(productoData);
            enviarDatos(productoData);
        }
    }

        // Crear objeto FormData para enviar datos del formulario
        const formData = new FormData();
        formData.append('nombre', nombre);
        formData.append('descripcion', descripcion);
        formData.append('genero', genero);
        formData.append('precio', precio);
        formData.append('estado', estado);
        formData.append('personalizable', personalizable);
        formData.append('idCategoria', idCategoria);
        formData.append('subIdCategoria', subIdCategoria);
        formData.append('image_Url', imagenSeleccionada);
        Object.entries(tallasSeleccionadas).forEach(([talla, cantidad]) => {
            formData.append(`talla${talla}`, cantidad);
        });

        // Enviar solicitud POST al servidor
        function enviarDatos(data){
            const url = `http://localhost:puerto/Apiweb/v1/producto/`;
            fetch(url, {
                method: 'POST',
                headers: {
                'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(response => {
                if (response.ok) {
                    return response.text().then(text => {
                        try {
                            return JSON.parse(text);
                        } catch {
                            alert('Datos enviados correctamente'); // pero la respuesta no es JSON.
                            window.location.href = "../pages/index.html";
                            return console.log(text);
                        }
                    });
                } else {
                    alert('Error al enviar los datos.');
                }
            })
            .catch(error => {
                console.error('Error en la solicitud Fetch:', error);
            });
        }   
    });
