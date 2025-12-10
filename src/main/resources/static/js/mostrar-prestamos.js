/*
GET /biblioteca/prestamosActivos -> List<MostrarPrestamoDTO>
GET /bibliotecaprestamosHistoricos -> List<MostrarPrestamoDTO>
GET /biblioteca/prestamosLector/{idLector} -> MostrarPrestamoDTO
class MostrarPrestamoDTO {
    Long idCopia;
    Long idLector;
    LocalDate inicio;
    LocalDate limite;
    LocalDate fin;
}
*/

$(document).ready(function () {
    $("#btnMostrarPrestamos").on("click", crearModalPrestamos);
});

function crearModalPrestamos() {
    // Eliminar si ya existe
    $("#modalVerPrestamo").remove();

    // Plantilla HTML del modal
    const modalHTML = `
    <div id="modalVerPrestamo" class="mostrar-prestamos-modal-overlay">
        <div class="mostrar-prestamos-modal">
            <div class="mostrar-prestamos-modal-header">Mostrar préstamos</div>

            <div class="mostrar-prestamos-modal-tabs">
                <div class="mostrar-prestamos-modal-tab active" data-tab="activos">Activos</div>
                <div class="mostrar-prestamos-modal-tab" data-tab="historico">Histórico</div>
                <div class="mostrar-prestamos-modal-tab" data-tab="lector">Por lector</div>
            </div>

            <div class="mostrar-prestamos-modal-body" id="modalBodyContent">
                <!-- Contenido dinámico -->
            </div>

            <div class="mostrar-prestamos-modal-footer">
                <button id="cerrarModalPrestamos">Cerrar</button>
            </div>
        </div>
    </div>`;

    $("body").append(modalHTML);

    // Cargar contenido inicial
    cargarTabla("activos");

    // Control de pestañas
    $(".mostrar-prestamos-modal-tab").on("click", function () {
        $(".mostrar-prestamos-modal-tab").removeClass("active");
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

    let tabla =
        `<table><thead><tr>
            <th>ID Copia</th>`;

    if (tab !== "lector")
        tabla += `<th>ID Lector</th>`

    tabla += `<th>Fecha de inicio</th>
              <th>Fecha límite</th>`;

    if (tab !== "activos")
        tabla += `<th>Fecha de fin</th>`;

    tabla += `</tr></thead>
              <tbody id="tbodyPrestamos"></tbody>
              </table>`;

    // Si es por lector añadimos input + botón
    if (tab === "lector") {
        tabla =
            `<input type="number" id="inputIdLector" placeholder="ID lector">
         <button id="btnBuscarPrestamosLector">Buscar</button>
         <br><br>` + tabla;
    }

    $("#modalBodyContent").html(tabla);

    switch(tab) {
        case "activos":
            cargarActivos();
            break;

        case "historico":
            cargarHistoricos();
            break;

        case "lector":
            $("#btnBuscarPrestamosLector").on("click", function () {
                const id = $("#inputIdLector").val();
                if (!id)
                    alert("Introduce un ID de lector");
                else
                    cargarPrestamoByIdLector(id);
            });
            break;
    }
}

// TABLAS DINÁMICAS POR PESTAÑA

function cargarActivos() {
    $.ajax({
        url: "/biblioteca/prestamosActivos",
        method: "GET",
        success: function (data) {
            rellenarTabla(data, true, false);
        }
    });
}

function cargarHistoricos() {
    $.ajax({
        url: "/biblioteca/prestamosHistoricos",
        method: "GET",
        success: function (data) {
            rellenarTabla(data, true, true);
        }
    });
}

function cargarPrestamoByIdLector(idLector) {
    $.ajax({
        url: "/biblioteca/prestamosLector/" + idLector,
        method: "GET",
        success: function (data) {
            rellenarTabla(data, false, true);
        },
        error: function (jqXHR) {
            closeAndShowMessage(jqXHR,
                "VerPrestamo",
                "No se pueden mostrar los préstamos.")
        }
    });
}

function rellenarTabla(lista, incluirLectorId, incluirFin) {

    let html = "";

    lista.forEach(p => {
        html += `
    <tr>
        <td>${p.idCopia}</td>`;
        if (incluirLectorId) {
            if (p.idLector === undefined || p.idLector === null)
                p.idLector = "";
            html += `<td>${p.idLector}</td>`;
        }

        html += `
        <td>${p.inicio}</td>
        <td>${p.limite}</td>`;

        if (incluirFin ) {
            if (p.fin === undefined || p.fin === null || p.fin === "null")
                p.fin = "";
            html += `<td>${p.fin}</td>`;
        }
        html += `</tr>`;
    });

    $("#tbodyPrestamos").html(html);
}