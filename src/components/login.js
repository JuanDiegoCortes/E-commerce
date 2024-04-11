const btnInicioSesion = document.getElementById('btn-iniciar-sesion');

btnInicioSesion.addEventListener('click', (event) => {
    event.preventDefault(); // Evita que el formulario se envíe automáticamente
    const cedula = 582347196 /* document.getElementById('cedula_usuario').value; */
    const contrasena = "N3j#m8!zQ1" /* document.getElementById('contrasena_usuario').value; */
    if (cedula === "" || contrasena === "") {
        return alert('Por favor, ingrese su cédula y contraseña');
    } else {
        fetchData(cedula, contrasena);
    }
})

function fetchData(cedula, contrasena) {
    const url = `http://localhost:8081/Apiweb/v1/usuario/autenticacionUsuario/${cedula}/${contrasena}`;
    fetch(url)
        .then(response => {
            console.log(response);
            return response.json()
        })
        .then(usuario => {
            if (usuario.contrasena !== contrasena) {
                alert('Cédula o contraseña incorrecta');
                throw new Error('Cédula o contraseña incorrecta');
            }
            console.log("Usuario autenticado", usuario);
            localStorage.setItem("usuario", JSON.stringify(usuario));
        })
        .catch(error => {
            console.error("Error al iniciar sesión:", error);
        });
}
