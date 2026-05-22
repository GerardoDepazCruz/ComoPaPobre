let warningTimer, logoutTimer;

function resetAdminTimers() {
    clearTimeout(warningTimer);
    clearTimeout(logoutTimer);

    warningTimer = setTimeout(() => {
        Swal.fire({
            title: '¿Administrador, sigues ahí?',
            text: 'Detectamos inactividad.',
            icon: 'warning',
            confirmButtonText: 'Sí, sigo trabajando',
            timer: 30000,
            timerProgressBar: true,
            allowOutsideClick: false,
            customClass: {
                popup: 'mi-popup',
                confirmButton: 'btn-confirmar'
            },
            buttonsStyling: false
        }).then((result) => {
            if (result.isConfirmed) {
                logoutTimer = setTimeout(() => {
                    Swal.fire({
                        icon: 'info',
                        title: 'Sesión cerrada',
                        text: 'Por seguridad, se cerró tu sesión por inactividad.',
                        timer: 3000,
                        showConfirmButton: false,
                        customClass: {
                            popup: 'mi-popup'
                        }
                    }).then(() => {
                        window.location.href = "/admin/logout";
                    });
                }, 30000);
            } else {
                window.location.href = "/admin/logout";
            }
        });
    }, 30000);
}

['mousemove', 'keydown', 'click', 'scroll'].forEach(evt =>
    document.addEventListener(evt, resetAdminTimers)
);

resetAdminTimers();
