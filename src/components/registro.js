window.onload = function() {
    const cedula = document.getElementById('cedula');
    const nombre = document.getElementById('nombre');
    const correo = document.getElementById('correo');
    const contrasena = document.getElementById('contrasena');
    const vContrasena = document.getElementById('vContrasena');
    const botonRegistro = document.getElementById('btnregistrar');

    botonRegistro.addEventListener('click', validarRegistro);

    function validarRegistro(event) {
        event.preventDefault();
        switch (true) {
            case cedula.value === '' || nombre.value === '' || correo.value === '' || contrasena.value === '' || vContrasena.value === '':
                Toastify({
                    text: "Todos los campos son obligatorios",
                    duration: 2000,
                    close: true,
                    gravity: "top", 
                    position: "right",
                    stopOnFocus: true,
                    style: {
                      background: "linear-gradient(to right, #4b33a8, #785ce9)",
                      borderRadius: "2rem",
                      textTransform: "uppercase",
                      fontSize: ".75rem"
                    },
                    offset: {
                        x: '1.5rem',
                        y: '1.5rem' 
                      },
                    onClick: function(){}
                    }).showToast();
                break

            case cedula.value.length !== 10:
                Toastify({
                    text: "Su cedula debe tener exactamente 10 digitos",
                    duration: 2000,
                    close: true,
                    gravity: "top", 
                    position: "right",
                    stopOnFocus: true,
                    style: {
                      background: "linear-gradient(to right, #4b33a8, #785ce9)",
                      borderRadius: "2rem",
                      textTransform: "uppercase",
                      fontSize: ".75rem"
                    },
                    offset: {
                        x: '1.5rem',
                        y: '1.5rem' 
                      },
                    onClick: function(){}
                    }).showToast(); 
                break;
            case correo !== '' && !correo.value.includes('@') || !correo.value.includes ('.'):
                Toastify({
                    text: "Correo invalido",
                    duration: 2000,
                    close: true,
                    gravity: "top",
                    position: "right",
                    stopOnFocus: true,
                    style: {
                        background: "linear-gradient(to right, #4b33a8, #785ce9)",
                        borderRadius: "2rem",
                        textTransform: "uppercase",
                        fontSize: ".75rem"
                    },
                    offset: {
                        x: '1.5rem',
                        y: '1.5rem'
                    },
                    onClick: function () { }
                }).showToast();
                break;
            case contrasena.value !== vContrasena.value:
                Toastify({
                    text: "Las contraseñas no coinciden",
                    duration: 2000,
                    close: true,
                    gravity: "top", 
                    position: "right",
                    stopOnFocus: true,
                    style: {
                      background: "linear-gradient(to right, #4b33a8, #785ce9)",
                      borderRadius: "2rem",
                      textTransform: "uppercase",
                      fontSize: ".75rem"
                    },
                    offset: {
                        x: '1.5rem',
                        y: '1.5rem' 
                      },
                    onClick: function(){}
                    }).showToast(); 
                break;
            default:
                crearUsuario(cedula.value, nombre.value, correo.value, contrasena.value);
                break; 
        }
    }

    function crearUsuario(cedulaValue, nombreValue, correoValue, contrasenaValue) {
        let usuario = {
            cedula: cedulaValue,
            nombre: nombreValue,
            correo: correoValue,
            roles: [
                {
                cedula: cedulaValue ,
                idRol: 1,
                contrasena: contrasenaValue
                }
            ]
        }

        const url = 'http://localhost:8081/Apiweb/v1/usuario/registroUsuario/';
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(usuario)
        }).then(response => response.ok)
        .then(data => {
            if (data) {
                Toastify({
                    text: "Usuario registrado con exito",
                    duration: 2000,
                    close: true,
                    gravity: "top",
                    position: "right",
                    stopOnFocus: true,
                    style: {
                        background: "linear-gradient(to right, #4b33a8, #785ce9)",
                        borderRadius: "2rem",
                        textTransform: "uppercase",
                        fontSize: ".75rem"
                    },
                    offset: {
                        x: '1.5rem',
                        y: '1.5rem'
                    },
                    onClick: function () { }
                }).showToast();
                setTimeout(() => {
                    window.location.href = 'login.html';
                }, 2000);
            } else {
                Toastify({
                    text: "Error al registrar usuario, cedula o correo en uso.",
                    duration: 2000,
                    close: true,
                    gravity: "top",
                    position: "right",
                    stopOnFocus: true,
                    style: {
                        background: "linear-gradient(to right, #4b33a8, #785ce9)",
                        borderRadius: "2rem",
                        textTransform: "uppercase",
                        fontSize: ".75rem"
                    },
                    offset: {
                        x: '1.5rem',
                        y: '1.5rem'
                    },
                    onClick: function () { }
                }).showToast();
            }
        })
    }

}