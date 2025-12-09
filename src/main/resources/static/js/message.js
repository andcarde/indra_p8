const MESSAGE_JS = true;

// ==== Helpers de modales ====
function abrirModal(selector) {
    $(selector).css("display", "flex");
}

function cerrarModal(selector) {
    $(selector).css("display", "none");
}

function closeAndShowMessage(jqXHR, modalName, byDefault) {
    cerrarModal("#modal" + modalName);
    let message = jqXHR.responseText;
    if (!message)
        message = byDefault;
    showMessage("No se ha podido prestar la copia.");
}

function showMessage(message) {
    // Crear un contenedor para el modal
    const modalDiv = document.createElement('div');
    modalDiv.classList.add('modal-fondo'); // fondo semi-transparente

    // Contenido del modal
    modalDiv.innerHTML = `
        <div class="modal">
            <h4>Mensaje</h4>
            <p>${message}</p>
            <div class="botones">
                <button class="btn-aceptar">Cerrar</button>
            </div>
        </div>
    `;

    // Añadir al body
    document.body.appendChild(modalDiv);

    // Función para cerrar modal
    function cerrarModal() {
        document.body.removeChild(modalDiv);
    }

    // Evento del botón Cerrar
    modalDiv.querySelector('.btn-aceptar').addEventListener('click', cerrarModal);

    // También cerrar al hacer clic fuera del modal
    modalDiv.addEventListener('click', function(e) {
        if (e.target === modalDiv) {
            cerrarModal();
        }
    });

    // Mostrar modal (flex display)
    modalDiv.style.display = 'flex';
}