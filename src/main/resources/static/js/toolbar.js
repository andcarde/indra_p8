$(document).ready(function () {
    // Mostrar modal de PRESTAR LIBRO
    $("#btnPrestar").on("click", function () {
        showPrestarModal();
    });

    // Mostrar modal CREAR LIBRO
    $("#btnCrearLibro").on("click", function () {
        showCrearLibroModal();
    });
});