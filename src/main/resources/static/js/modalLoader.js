//document.querySelectorAll('.openModal').forEach(button => {
//    button.addEventListener('click', function(event) {
//        event.preventDefault(); // Prevent default button behavior
//        var url = this.getAttribute('data-url');
//        var modalId = this.getAttribute('data-modal-id');
//
//        fetch(url)
//            .then(response => response.text())
//            .then(html => {
//                // Remove existing modal if present
//                let existingModal = document.getElementById(modalId);
//                if (existingModal) {
//                    existingModal.remove();
//                }
//
//                // Insert the modal content into the body
//                document.body.insertAdjacentHTML('beforeend', html);
//
//                // Show the new modal
//                var myModal = new bootstrap.Modal(document.getElementById(modalId));
//                myModal.show();
//            })
//            .catch(err => console.error('Error loading the modal:', err));
//    });
//});