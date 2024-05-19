document.addEventListener('DOMContentLoaded', (event) => {
    let ciudadSeleccionadaId = "";
    let modalidadEntregaSeleccionada = "";
    let metodoPagoSeleccionado = "";

    document.querySelectorAll('.boton-metodoPago').forEach(button => {
        button.addEventListener('click', function() {
            metodoPagoSeleccionado = this.id;
        });
    });

    document.querySelectorAll('.boton-ciudad').forEach(button => {
        button.addEventListener('click', function() {
            ciudadSeleccionadaId = parseInt(this.id);
        });
    });

    document.querySelectorAll('.boton-modalidadEntrega').forEach(button => {
        button.addEventListener('click', function() {
            modalidadEntregaSeleccionada = this.id;
        });
    });

    document.querySelectorAll('.boton-metodoPago').forEach(button => {
        button.addEventListener('click', function() {
            const metodoPago = this.id;
            const orden = JSON.parse(localStorage.getItem("orden"));
            orden.metodoPago = metodoPago;
            localStorage.setItem("orden", JSON.stringify(orden));
        });
    });

    let botonPago = document.getElementById("botonPago");
    botonPago.addEventListener("click", capturarValores);

    function capturarValores() {
        let nombre = document.getElementById('nombre').value;
        let apellido = document.getElementById('apellido').value;
        let telefono = document.getElementById('numTelefono').value;
        let direccion = document.getElementById('direccion').value;
        let codigoPostal = document.getElementById('codigoPostal').value;
        let referencias = document.getElementById('referencias').value;

        if (nombre === "" || apellido === "" || codigoPostal === "" || ciudadSeleccionadaId === "" || modalidadEntregaSeleccionada === "" || telefono === "" || direccion === "" || metodoPagoSeleccionado === "") {
            Toastify({
                text: "Por favor, complete todos los campos.",
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
            return "";
        } else {
            const envioData = {
                nombre: nombre,
                apellido: apellido,
                direccion: direccion,
                modalidadEntrega: modalidadEntregaSeleccionada,
                telefono: telefono,
                idCiudad: {
                    idCiudad: ciudadSeleccionadaId
                },
                codigoPostal: codigoPostal,
                referencias: referencias,
            };

            let ordenObj = localStorage.getItem("orden");
            let orden = JSON.parse(ordenObj);
            orden.idEnvio = envioData;
            localStorage.setItem("orden", JSON.stringify(orden));

            crearOrden(orden);
        }
    }
    function crearOrden(data) {
        const url = `http://localhost:8081/Apiweb/v1/orden/`;
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            return response;
        })
        .then(response => {
            if (response.ok) {
                response.text().then(text => {
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
                    setTimeout(() => {
                        localStorage.removeItem("orden");
                        localStorage.removeItem("productos-en-carrito");
                        window.location.href = "../pages/index.html";
                    }, 2000);
                });
            } else {
                response.text().then(text => {
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
    }

});
