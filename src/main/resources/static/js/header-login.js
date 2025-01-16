document.querySelectorAll('.form-select, .dropdown-menu input').forEach(element => {
    element.addEventListener('change', function() {
        this.form.submit();
    });
});

function toggleDropdown() {
    const dropdown = document.querySelector('.dropdown-multiselect');
    dropdown.classList.toggle('open');
}

document.addEventListener('click', function (event) {
    const dropdown = document.querySelector('.dropdown-multiselect');
    if (!dropdown.contains(event.target)) {
        dropdown.classList.remove('open');
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const dynamicModal = document.getElementById('dynamicModal');
    const modalTitle = document.getElementById('modalTitle');
    const modalBody = document.getElementById('modalBody');

    const bootstrapModal = new bootstrap.Modal(dynamicModal, {
        backdrop: true,
        keyboard: true,
        focus: true,
    });

    // Якщо є параметр loginRequired, відкриваємо модальне вікно з формою входу
    if (urlParams.has('loginRequired')) {
        modalTitle.textContent = 'Вхід на сайт';
        loadModalContent('login');
        bootstrapModal.show();
    }

    // Слухаємо події відкриття модального вікна
    dynamicModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const modalType = button.getAttribute('data-modal-type');
        loadModalContent(modalType);
    });

    // Функція завантаження контенту в модальне вікно
    function loadModalContent(modalType) {
        modalBody.innerHTML = `
            <div class="text-center">
                <div class="spinner-border" role="status">
                    <span class="visually-hidden">Завантаження...</span>
                </div>
            </div>
        `;

        let fetchUrl;
        if (modalType === 'login') {
            modalTitle.textContent = 'Вхід на сайт';
            fetchUrl = '/auth/login';
        } else if (modalType === 'registration') {
            modalTitle.textContent = 'Реєстрація на сайті';
            fetchUrl = '/auth/registration';
        }

        // Виконуємо AJAX-запит
        fetch(fetchUrl, {
            headers: { 'X-Requested-With': 'XMLHttpRequest' },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.text();
            })
            .then(html => {
                modalBody.innerHTML = html;

                // Ініціалізація форми після завантаження
                const form = modalBody.querySelector('form');
                if (form) {
                    form.addEventListener('submit', function (e) {
                        e.preventDefault();
                        submitForm(this);
                    });
                }

                // Підключення обробників для перемикання модальних вікон
                attachSwitchHandler();
            })
            .catch(err => {
                console.error('Помилка завантаження:', err);
                modalBody.innerHTML = `
                    <div class="alert alert-danger" role="alert">
                        Помилка завантаження форми. Будь ласка, спробуйте пізніше.
                        <button type="button" class="btn btn-link" onclick="loadModalContent('${modalType}')">
                            Спробувати ще раз
                        </button>
                    </div>
                `;
            });
    }
    // Функція для відправки форми
    function submitForm(form) {
        const formData = new FormData(form);

        fetch(form.action, {
            method: 'POST',
            body: formData,
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url;
                } else {
                    return response.text().then(html => {
                        if (response.ok) {
                            // Перезавантажуємо сторінку при успішній авторизації
                            window.location.reload();
                        } else {
                            // Оновлюємо контент модального вікна, якщо є помилки
                            modalBody.innerHTML = html;
                            attachSwitchHandler();
                        }
                    });
                }
            })
            .catch(error => {
                console.error('Error:', error);
                modalBody.innerHTML = `
            <div class="alert alert-danger">
                Помилка при відправці форми. Спробуйте ще раз.
            </div>
        `;
            });
    }

    // Обробник перемикання між модальними формами
    function attachSwitchHandler() {
        const switchButtons = modalBody.querySelectorAll('.switch-modal');
        switchButtons.forEach(button => {
            button.addEventListener('click', function (event) {
                event.preventDefault();
                const targetModalType = this.getAttribute('data-modal-type');
                loadModalContent(targetModalType);
            });
        });
    }
});

