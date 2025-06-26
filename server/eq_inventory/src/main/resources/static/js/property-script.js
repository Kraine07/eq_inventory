document.addEventListener('DOMContentLoaded', function () {

    // property
    const propertyForm = document.getElementById('property-form');
    const propertyFormContainer = document.getElementById('property-form-container');
    const openPropertyFormButton = document.getElementById('open-property-form-btn');
    const closePropertyForm = document.getElementById('close-property-form');

    const editPropertyButton = document.querySelectorAll('.edit-property');
    const deletePropertyButton = document.querySelectorAll('.delete-property');

    // location
    const locationForm = document.getElementById('location-form');
    const locationFormContainer = document.getElementById('location-form-container');
    const openLocationFormButton = document.getElementById('open-location-form-btn');
    const closeLocationForm = document.getElementById('close-location-form');

    const editLocationButton = document.querySelectorAll('.edit-location');
    const deleteLocationButton = document.querySelectorAll('.delete-location');


    // Open property form
    if (openPropertyFormButton !== null) {
        openPropertyFormButton.addEventListener("click", function () {

            // scroll to top
            window.scrollTo({ top: 0, behavior: "smooth" });
            document.body.classList.add('overflow-hidden');
            propertyFormContainer.style.display = "block";
            propertyForm.reset(); // Reset the form fields
        });
    }

    // Close property form
    if (closePropertyForm !== null) {
        closePropertyForm.addEventListener("click", function () {
            document.body.classList.remove('overflow-hidden');
            propertyFormContainer.style.display = "none";
            propertyForm.reset(); // Reset the form fields
        });
    }

    // clic outside the form to close it
    window.onclick = function(event) {
        if (event.target === propertyFormContainer) {
            document.body.classList.remove('overflow-hidden');
            propertyFormContainer.style.display = "none";
            propertyForm.reset(); // Reset the form fields
        }

        if (event.target === locationFormContainer) {
            document.body.classList.remove('overflow-hidden');
            locationFormContainer.style.display = "none";
            locationForm.reset(); // Reset the form fields
        }
    };


    // edit & delete property
    if (editPropertyButton.length > 0 || deletePropertyButton.length > 0) {

        // Edit property
        editPropertyButton.forEach(editButton => {
            editButton.addEventListener('click', function () {
                const propertyRegion = document.getElementById('property-region');
                const propertyName = document.getElementById('property-name');
                const propertyUser = document.getElementById('property-user');

                // Set the form fields with the property data
                propertyName.value = editButton.parentElement.previousElementSibling.textContent.trim();
                propertyRegion.value =editButton.parentElement.parentElement.nextElementSibling.firstElementChild.children[0].textContent.trim();
                propertyUser.value = editButton.parentElement.parentElement.nextElementSibling.firstElementChild.children[1].textContent.trim();

                 // scroll to top & disable body scroll
                window.scrollTo({ top: 0, behavior: "smooth" });
                document.body.classList.add('overflow-hidden');

                // Show the form
                propertyFormContainer.style.display = "block";
            });
        });



        // Delete property
        deletePropertyButton.forEach(deleteButton => {
            deleteButton.addEventListener("click", function () {

                // delete property logic
            });
        });



        // LOCATION
        //open location form
        if (openLocationFormButton !== null) {
            openLocationFormButton.addEventListener("click", function () {

                // scroll to top
                window.scrollTo({ top: 0, behavior: "smooth" });
                document.body.classList.add('overflow-hidden');
                locationFormContainer.style.display = "block";
                locationForm.reset(); // Reset the form fields
            });
        }

        // Close location form
        if (closeLocationForm !== null) {
            closeLocationForm.addEventListener("click", function () {
                document.body.classList.remove('overflow-hidden');
                locationFormContainer.style.display = "none";
                locationForm.reset(); // Reset the form fields
            });
        }

        // edit & delete location
        if (editLocationButton.length > 0 || deleteLocationButton.length > 0) {

            // Edit location
            editLocationButton.forEach(editButton => {
                editButton.addEventListener('click', function () {
                    const locationName = document.getElementById('location-name');
                    const locationProperty= document.getElementById('location-property');

                    // Set the form fields with the location data
                    locationName.value = editButton.parentElement.parentElement.children[3].textContent.trim();
                    locationProperty.value = editButton.parentElement.parentElement.children[2].textContent.trim();
                     // scroll to top & disable body scroll
                    window.scrollTo({ top: 0, behavior: "smooth" });
                    document.body.classList.add('overflow-hidden');

                    // Show the form
                    locationFormContainer.style.display = "block";
                });
            });

            // Delete location
            deleteLocationButton.forEach(deleteButton => {
                deleteButton.addEventListener("click", function () {

                    // delete location logic
                });
            });
        }

    }


});