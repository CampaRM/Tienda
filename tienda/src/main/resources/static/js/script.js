function toggleVista(mostrarId, ocultarId) {
    document.getElementById(mostrarId).style.display = 'block';
    document.getElementById(ocultarId).style.display = 'none';
}


function confirmarAccion(boton) {
    const url = boton.getAttribute('data-url');
    fetch(url, { method: 'DELETE' }).then(() => location.reload());
}