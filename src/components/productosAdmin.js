document.addEventListener("DOMContentLoaded", function() {
    let generoSeleccionado = "";
    let estadoSeleccionado = "";
    let personalizableSeleccionado = "";
    let categoriaSeleccionadaId = "";
    let tallasSeleccionadas = {};
    let imagenSeleccionada = document.getElementById('image_Url').value;
    ['XS', 'S', 'M', 'L', 'XL'].forEach(talla => {
        document.getElementById(`talla${talla}`).addEventListener('change', function() {
            if (this.checked) {
                let cantidad = parseInt(document.getElementById(`cantidad${talla}`).value);  // Asegúrate de que 'cantidad' es un número
                let idTalla = this.dataset.id;  // Obtener el ID de la talla del atributo data-id
                if (!isNaN(cantidad)) {  // Si 'cantidad' es un número, añadirlo a 'tallasSeleccionadas'
                    tallasSeleccionadas[idTalla] = cantidad;  // Usar el ID de la talla como clave en lugar de la talla
                }
            } else {
                let idTalla = this.dataset.id;  // Obtener el ID de la talla del atributo data-id
                delete tallasSeleccionadas[idTalla];  // Usar el ID de la talla como clave en lugar de la talla
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

    document.querySelectorAll('.boton-personalizable').forEach(button => {
        button.addEventListener('click', function() {
            personalizableSeleccionado = this.id;
        });
    });

    document.querySelectorAll('.boton-categoria').forEach(button => {
        button.addEventListener('click', function() {
            categoriaSeleccionadaId = parseInt(this.id);
        });
    });

    let botonCrear = document.getElementById("botonCrear");
    botonCrear.addEventListener("click", capturarValores);

    // Función para capturar valores y enviar la solicitud
    function capturarValores() {
        let nombre = document.getElementById('nombre').value;
        let descripcion = document.getElementById('descripcion').value;
        let precio = parseFloat(document.getElementById('precio').value);
        imagenSeleccionada = document.getElementById('image_Url').value;

        // Validar campos requeridos
        if (nombre === "" || descripcion === "" || isNaN(precio) || generoSeleccionado === "" || estadoSeleccionado === "" || isNaN(categoriaSeleccionadaId) || Object.keys(tallasSeleccionadas).length === 0 || imagenSeleccionada === ""){            
            alert('Por favor, complete todos los campos.');
            return "";
        } else {
            const productoData = {
                nombre: nombre,
                descripcion: descripcion,
                precio: precio,
                genero: generoSeleccionado,
                estado: estadoSeleccionado,
                idCategoria: categoriaSeleccionadaId,
                image_Url: imagenSeleccionada,
                idTallas: Object.entries(tallasSeleccionadas).map(([idTalla, cantidad]) => ({idTalla: parseInt(idTalla), cantidad: parseInt(cantidad)}))
            };
            console.log(productoData);
            enviarDatos(productoData);
        }
    }

    // Función para enviar la solicitud POST al servidor
    function enviarDatos(data){
        const url = `http://localhost:8081/Apiweb/v1/producto/`;
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