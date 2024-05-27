document.addEventListener("DOMContentLoaded", function() {
    let generoSeleccionado = "";
    let estadoSeleccionado = "";
    let personalizableSeleccionado = "";
    let categoriaSeleccionadaId = "";

    document.querySelectorAll(".cantidadXS").forEach(el => el.style.display = "none");
    document.querySelectorAll(".cantidadS").forEach(el => el.style.display = "none");
    document.querySelectorAll(".cantidadM").forEach(el => el.style.display = "none");
    document.querySelectorAll(".cantidadL").forEach(el => el.style.display = "none");
    document.querySelectorAll(".cantidadXL").forEach(el => el.style.display = "none");
    document.querySelectorAll(".cantidadUNICA").forEach(el => el.style.display = "none");

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

    document.querySelectorAll('.dropdown-menu .boton-categoria').forEach(button => {
        button.addEventListener('click', function() {
            categoriaSeleccionadaId = parseInt(this.id);
            if (categoriaSeleccionadaId === 15) {
                document.querySelectorAll(".cantidadXS").forEach(el => el.style.display = "none");
                document.querySelectorAll(".cantidadS").forEach(el => el.style.display = "none");
                document.querySelectorAll(".cantidadM").forEach(el => el.style.display = "none");
                document.querySelectorAll(".cantidadL").forEach(el => el.style.display = "none");
                document.querySelectorAll(".cantidadXL").forEach(el => el.style.display = "none");
                document.querySelectorAll(".cantidadUNICA").forEach(el => el.style.display = "block");
            } else {
                document.querySelectorAll(".cantidadXS").forEach(el => el.style.display = "block");
                document.querySelectorAll(".cantidadS").forEach(el => el.style.display = "block");
                document.querySelectorAll(".cantidadM").forEach(el => el.style.display = "block");
                document.querySelectorAll(".cantidadL").forEach(el => el.style.display = "block");
                document.querySelectorAll(".cantidadXL").forEach(el => el.style.display = "block");
                document.querySelectorAll(".cantidadUNICA").forEach(el => el.style.display = "none");
            }
        });
    });

    let botonCrear = document.querySelector(".botonCrear");
    botonCrear.addEventListener("click", capturarValores);

    // Función para capturar valores y enviar la solicitud
    function capturarValores() {
        let nombre = document.getElementById('nombre').value;
        let descripcion = document.getElementById('descripcion').value;
        let precio = parseFloat(document.getElementById('precio').value);
        let imagenSeleccionada = document.getElementById('image_Url').value;
        let tallaXS = parseInt(document.querySelector('.cantidadXS').value || 0);
        let tallaS = parseInt(document.querySelector('.cantidadS').value || 0);
        let tallaM = parseInt(document.querySelector('.cantidadM').value || 0);
        let tallaL = parseInt(document.querySelector('.cantidadL').value || 0);
        let tallaXL = parseInt(document.querySelector('.cantidadXL').value || 0);
        let tallaUNICA = parseInt(document.querySelector('.cantidadUNICA').value || 0);

        let Tallas = [
            {idTalla: 1, cantidad: tallaXS},
            {idTalla: 2, cantidad: tallaS},
            {idTalla: 3, cantidad: tallaM},
            {idTalla: 4, cantidad: tallaL},
            {idTalla: 5, cantidad: tallaXL},
            {idTalla: 6, cantidad: tallaUNICA},
        ];

        Tallas = Tallas.filter(talla => !(talla.cantidad === 0));

        // Validar campos requeridos
        if (nombre === "" || descripcion === "" || isNaN(precio) || generoSeleccionado === "" || estadoSeleccionado === "" || isNaN(categoriaSeleccionadaId) || (Tallas == null) || imagenSeleccionada === ""){            
            alert('Por favor, complete todos los campos.');
            return "";
        } else {
            const productoData = {
                nombre: nombre,
                descripcion: descripcion,
                precio: precio,
                estado: estadoSeleccionado,
                personalizable: personalizableSeleccionado,
                image_Url: imagenSeleccionada,
                genero: generoSeleccionado,
                idCategoria: {
                    idCategoria: categoriaSeleccionadaId
                },
                Tallas
            };
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
                    Toastify({
                        text: text,
                        duration: 2000,
                        close: true,
                        gravity: "top",
                        position: "right",
                        stopOnFocus: true,
                        style: {
                            background: "linear-gradient(to right, #4b33a8, #785ce9)",
                            borderRadius: "2rem",
                        }
                    }).showToast();
                });
            } else {
                return response.text().then(text => {
                    Toastify({
                        text: text,
                        duration: 2000,
                        close: true,
                        gravity: "top",
                        position: "right",
                        stopOnFocus: true,
                        style: {
                            background: "linear-gradient(to right, #4b33a8, #785ce9)",
                            borderRadius: "2rem",
                        }
                    }).showToast();
                });
            }
        })
        .catch(error => {
            console.error('Error en la solicitud Fetch:', error);
        });
    }
});