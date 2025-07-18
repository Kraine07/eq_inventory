


import { confirmationQuestion } from "./script.js";
import { confirmationHeading } from "./script.js";
import { confirmationWindow } from "./script.js";
import { statusConfirmationButton } from "./script.js";

import { deleteConfirmationButton } from "./script.js";





document.addEventListener('DOMContentLoaded', function () {

    // property
    const propertyForm = document.getElementById('property-form');
    const propertyFormContainer = document.getElementById('property-form-container');
    const openPropertyFormButton = document.getElementById('open-property-form-btn');
    const closePropertyForm = document.getElementById('close-property-form-btn');
    const editPropertyButton = document.querySelectorAll('.edit-property');
    const deletePropertyButton = document.querySelectorAll('.delete-property');

    // location
    const locationForm = document.getElementById('location-form');
    const locationFormContainer = document.getElementById('location-form-container');
    const openLocationFormButton = document.getElementById('open-location-form-btn');
    const closeLocationForm = document.getElementById('close-location-form-btn');
    const editLocationButton = document.querySelectorAll('.edit-location');
    const deleteLocationButton = document.querySelectorAll('.delete-location');


    // Open property form
    if (openPropertyFormButton !== null) {
        openPropertyFormButton.addEventListener("click", function () {

            // scroll to top
            window.scrollTo({ top: 0, behavior: "smooth" });
            document.querySelector("#property-form-title").textContent = "Create Property";
            document.querySelector("#property-form-submit").textContent = "Create Property";
            document.body.classList.add('overflow-hidden');
            propertyFormContainer.style.display = "block";
            propertyForm.action = "/add-property"; // Set the form action to create
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
                const propertyId = document.getElementById('property-id');
                const propertyRegion = document.getElementById('property-region');
                const propertyName = document.getElementById('property-name');
                const propertyUser = document.getElementById('property-user');

                // Set the form fields with the property data
                propertyId.value = this.parentElement.previousElementSibling.previousElementSibling.textContent.trim();
                propertyName.value = this.parentElement.previousElementSibling.textContent.trim();
                propertyRegion.value = this.parentElement.parentElement.nextElementSibling.firstElementChild.children[0].textContent.trim();
                propertyUser.value = this.parentElement.parentElement.nextElementSibling.firstElementChild.children[1].textContent.trim();

                // scroll to top & disable body scroll
                window.scrollTo({ top: 0, behavior: "smooth" });
                document.body.classList.add('overflow-hidden');

                //set form heading and button text
                document.querySelector("#property-form-title").textContent = "Update Property";
                document.querySelector("#property-form-submit").textContent = "Update Property";


                // Show the form
                propertyFormContainer.style.display = "block";
            });
        });



        // Delete property
        deletePropertyButton.forEach(deleteButton => {
            deleteButton.addEventListener("click", function () {
                const deleteId = document.querySelector("#delete-property-id");

                // delete property logic
                deleteId.value = this.parentElement.previousElementSibling.previousElementSibling.textContent.trim();

                deleteConfirmationButton.setAttribute("form", "delete-property");

                statusConfirmationButton.style.display = "none";
                deleteConfirmationButton.style.display = "block";
                confirmationHeading.textContent = "Delete Property";
                confirmationQuestion.textContent = "Please note, this action is irreversible and will fail if equipment exist on the property. Do you want to delete this property and all its locations?";


                document.body.classList.add('overflow-hidden');
                window.scrollTo({ top: 0, behavior: "smooth" });
                confirmationWindow.style.display = "block";

            });
        });

    }

        // LOCATION
        //open location form
        if (openLocationFormButton !== null) {
            openLocationFormButton.addEventListener("click", function () {

                // scroll to top
                window.scrollTo({ top: 0, behavior: "smooth" });
                document.querySelector("#location-form-title").textContent = "Create Location";
                document.querySelector("#location-form-submit").textContent = "Create Location";
                document.body.classList.add('overflow-hidden');
                locationFormContainer.style.display = "block";
                locationForm.action = "/add-location"; // Set the form action to create
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
                    const locationProperty = document.getElementById('location-property');

                    const propertyIdValue = this.parentElement.parentElement.children[2].textContent;
                    const locationNameValue = this.parentElement.parentElement.children[3].textContent;

                    //set id
                    document.querySelector("#location-id").value = propertyIdValue + "," + locationNameValue;

                    // Set the form fields with the location data
                    locationName.value = this.parentElement.parentElement.children[3].textContent.trim();
                    locationProperty.value = this.parentElement.parentElement.children[2].textContent.trim();
                     // scroll to top & disable body scroll
                    window.scrollTo({ top: 0, behavior: "smooth" });
                    document.body.classList.add('overflow-hidden');


                    //set form heading and button text
                    document.querySelector("#location-form-title").textContent = "Update Location";
                    document.querySelector("#location-form-submit").textContent = "Update Location";

                    // Show the form
                    locationFormContainer.style.display = "block";
                });
            });

            // Delete location
            deleteLocationButton.forEach(deleteButton => {
                deleteButton.addEventListener("click", function () {

                    const propertyIdValue = this.parentElement.parentElement.children[2].textContent;
                    const locationNameValue = this.parentElement.parentElement.children[3].textContent;

                    //set id
                    document.querySelector("#delete-location-id").value = propertyIdValue + "," + locationNameValue;

                    //set texts
                    confirmationHeading.textContent = "Delete Location";
                    confirmationQuestion.textContent = "This action is permanent. Do you want to delete this location?";

                    deleteConfirmationButton.setAttribute("form", "delete-location");

                    statusConfirmationButton.style.display = "none";
                    deleteConfirmationButton.style.display = "block";


                    document.body.classList.add('overflow-hidden');
                    window.scrollTo({ top: 0, behavior: "smooth" });
                    confirmationWindow.style.display = "block";
                });
            });
        }



});