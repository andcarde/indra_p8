function showMessage(message) {
    // Crear un contenedor div para el modal
    const modalDiv = document.createElement('div');
    modalDiv.innerHTML = `
    <div class="modal fade" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Mensaje</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
          </div>
          <div class="modal-body">
            <p>${message}</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Cerrar</button>
          </div>
        </div>
      </div>
    </div>
    `;

    // Añadir el modal al body
    document.body.appendChild(modalDiv);

    // Inicializar y mostrar el modal con Bootstrap
    const bootstrapModal = new bootstrap.Modal(modalDiv.querySelector('.modal'));
    bootstrapModal.show();

    // Eliminar el modal del DOM cuando se cierre
    modalDiv.querySelector('.modal').addEventListener('hidden.bs.modal', () => {
        modalDiv.remove();
    });
}