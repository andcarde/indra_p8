$(document).ready(function () {
    $("#btnMostrarPrestamos").on("click", crearModalPrestamos);

    function crearModalPrestamos() {
        // Eliminar si ya existe
        $("#modalVerPrestamo").remove();

        // Plantilla HTML del modal
        const modalHTML = `
        <div id="modalVerPrestamo" class="custom-modal-overlay">
            <div class="custom-modal">
                <div class="custom-modal-header">Mostrar préstamos</div>

                <div class="custom-modal-tabs">
                    <div class="custom-modal-tab active" data-tab="activos">Activos</div>
                    <div class="custom-modal-tab" data-tab="historico">Histórico</div>
                    <div class="custom-modal-tab" data-tab="lector">Por lector</div>
                </div>

                <div class="custom-modal-body" id="modalBodyContent">
                    <!-- Contenido dinámico -->
                </div>

                <div class="custom-modal-footer">
                    <button id="cerrarModalPrestamos">Cerrar</button>
                </div>
            </div>
        </div>`;

        $("body").append(modalHTML);

        // Cargar contenido inicial
        cargarTabla("activos");

        // Control de pestañas
        $(".custom-modal-tab").on("click", function () {
            $(".custom-modal-tab").removeClass("active");
            $(this).addClass("active");

            const tab = $(this).data("tab");
            cargarTabla(tab);
        });

        // Botón cerrar
        $("#cerrarModalPrestamos").on("click", function () {
            $("#modalVerPrestamo").remove();
        });
    }

    // Función para cargar tablas
    function cargarTabla(tab) {
        let tabla = `<table><thead><tr>
                     <th>ID Copia</th>`;
        if (tab !== "lector")
            tabla += `<th>ID Lector</th>`
        tabla += `<th>Fecha de inicio</th>
                  <th>Fecha límite</th>`
        if (tab !== "activos")
            tabla += `<th>Fecha de fin</th>`;
        tabla += `</tr></thead></tbody></table>`;
        $("#modalBodyContent").html(tabla);
        switch(tab) {
            case "activos":
                cargarActivos();
                break;
            case "historicos":
            // Completar
        }
    }

    // TABLAS DINÁMICAS POR PESTAÑA

    function cargarActivos() {

    }

    function cargarHistoricos() {

    }

    function cargarPrestamoByIdLector(idLector) {

    }

    /* Enviado desde Java: List<MostrarPrestamoDTO
    public class MostrarPrestamoDTO {

        private Long idCopia;
        private Long idLector;
        private LocalDate inicio;
        private LocalDate limite;
        private LocalDate fin;
    }
    */
});