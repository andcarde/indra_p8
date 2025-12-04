function showCrearLibroModal() {
    const modalDiv = $(`
    <div class="modal fade" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

          <div class="modal-header">
            <h5 class="modal-title">Crear libro</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>

          <div class="modal-body">

            <div class="mb-3">
              <label class="form-label">Título</label>
              <input type="text" id="crear_titulo" class="form-control">
            </div>

            <div class="mb-3">
              <label class="form-label">Tipo de libro</label>
              <select id="crear_tipo" class="form-select">
                <option value="NOVELA">Novela</option>
                <option value="ENSAYO">Ensayo</option>
                <option value="MANUAL">Manual</option>
                <option value="TECNOLOGIA">Tecnología</option>
                <option value="HISTORIA">Historia</option>
              </select>
            </div>

            <div class="mb-3">
              <label class="form-label">Editorial</label>
              <input type="text" id="crear_editorial" class="form-control">
            </div>

            <div class="mb-3">
              <label class="form-label">Año de publicación</label>
              <input type="number" id="crear_anyo" class="form-control">
            </div>

            <div class="mb-3">
              <label class="form-label">ID Autor</label>
              <input type="number" id="crear_idAutor" class="form-control">
            </div>

          </div>

          <div class="modal-footer">
            <button id="btnConfirmarCrearLibro" class="btn btn-primary">Confirmar</button>
          </div>

        </div>
      </div>
    </div>
    `);

    // Insertar modal en el DOM
    $("body").append(modalDiv);

    // Activar Bootstrap modal
    const modal = new bootstrap.Modal(modalDiv[0]);
    modal.show();

    // Confirmar
    modalDiv.find("#btnConfirmarCrearLibro").on("click", function () {

        const dto = {
            titulo: $("#crear_titulo").val(),
            tipo: $("#crear_tipo").val(),
            editorial: $("#crear_editorial").val(),
            anyo: parseInt($("#crear_anyo").val()),
            idAutor: parseInt($("#crear_idAutor").val())
        };

        $.ajax({
            url: "/crearlibro",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(dto),

            success: function () {
                modal.hide();
                showMessage("Aviso", "El libro ha sido creado correctamente.");
            },
            error: function () {
                modal.hide();
                showMessage("Error", "No se ha podido crear el libro.");
            }
        });
    });

    // Eliminar modal al cerrarlo
    modalDiv.on("hidden.bs.modal", () => modalDiv.remove());
}