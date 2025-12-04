function showPrestarModal() {

    // Crear dinámicamente el modal
    const modalDiv = $(`
    <div class="modal fade" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

          <div class="modal-header">
            <h5 class="modal-title">Prestar libro</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>

          <div class="modal-body">
            <div class="mb-3">
              <label class="form-label">ID Copia</label>
              <input type="number" id="modal_idCopia" class="form-control">
            </div>

            <div class="mb-3">
              <label class="form-label">ID Lector</label>
              <input type="number" id="modal_idLector" class="form-control">
            </div>
          </div>

          <div class="modal-footer">
            <button id="btnConfirmarPrestamo" class="btn btn-primary">Confirmar</button>
          </div>

        </div>
      </div>
    </div>
    `);

    // Añadir al body
    $('body').append(modalDiv);

    // Crear instancia de modal Bootstrap
    const modal = new bootstrap.Modal(modalDiv[0]);
    modal.show();

    // Acción del botón confirmar
    modalDiv.find("#btnConfirmarPrestamo").on("click", function () {
        let idCopia = $("#modal_idCopia").val();
        let idLector = $("#modal_idLector").val();

        $.ajax({
            url: `/prestar/${idLector}/${idCopia}`,
            type: "POST",
            success: function (message) {
                modal.hide();
                if (!message)
                    showMessage("Aviso", "Se ha prestado la copia.");
                else
                    showMessage("Aviso", message);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                modal.hide();
                showMessage("Error", "No se ha podido prestar la copia.");
            }
        });
    });

    // Eliminar modal del DOM al cerrarse
    modalDiv.on('hidden.bs.modal', function () {
        modalDiv.remove();
    });
}