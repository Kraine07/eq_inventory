

import { confirmationQuestion } from "./script.js";
import { confirmationHeading } from "./script.js";
import { confirmationWindow } from "./script.js";
import { statusConfirmationButton } from "./script.js";

import { deleteConfirmationButton } from "./script.js";



document.addEventListener("DOMContentLoaded", function () {

    //
    const equipmentForm = document.getElementById("equipment-form");
    const equipmentFormContainer = document.getElementById("equipment-form-container");
    const openEquipmentForm = document.getElementById("open-equipment-form-btn");
    const closeEquipmentForm = document.querySelector("#close-equipment-form-btn");

    //edit & delete equipment
    const editEquipmentButton = document.querySelectorAll(".edit-equipment");
    const deleteEquipmentButton = document.querySelectorAll(".delete-equipment");

    // equipment form fields
    const equipmentId = document.querySelector("#equipment-id");
    const equipmentModel = document.querySelector("#equipment-model");
    const equipmentLocation = document.querySelector("#equipment-location");
    const serialNumber = document.querySelector("#serial-number");
    const manufacturedDate = document.querySelector("#manufactured-date");




    // MANUFACTURER
    const manufacturerFormModal = document.getElementById("manufacturer-form-modal");
    const openManufacturerForm = document.getElementById("open-manufacturer-form-btn");
    const closeManufacturerForm = document.querySelector("#close-manufacturer-form-btn");
    const manufacturerForm = document.querySelector("#manufacturer-form");
    const editManufacturerButton = document.querySelectorAll(".edit-manufacturer");
    const deleteManufacturerButton = document.querySelectorAll(".delete-manufacturer");

    // manufacturer form fields
    const manufacturerId = document.querySelector("#manufacturer-id");
    const manufacturerName = document.querySelector("#manufacturer-name");

    const manufacturerFormSubmit = document.querySelector("#manufacturer-form-submit");
    const manufacturerFormHeading = document.querySelector("#manufacturer-form-heading");



    //MODEL
    const modelFormModal = document.querySelector("#model-form-modal");
    const openModelForm = document.querySelector("#open-model-form-btn");
    const closeModelForm = document.querySelector("#close-model-form-btn");
    const modelForm = document.querySelector("#model-form");
    const editModelButton = document.querySelectorAll(".edit-model");
    const deleteModelButton = document.querySelectorAll(".delete-model");







    // open equipment form
    if (openEquipmentForm !== null) {
        openEquipmentForm.addEventListener("click", function () {
            // scroll to top
            window.scrollTo({ top: 0, behavior: "smooth" });

            // stop scrolling
            document.body.classList.add('overflow-hidden');

            // show form
            equipmentFormContainer.style.display = "block";

        });
    }


    // close Equipment form with button
    if (closeEquipmentForm !== null) {
        closeEquipmentForm.addEventListener("click", function () {
            document.body.classList.remove('overflow-hidden');

            equipmentFormContainer.style.display = "none";
            equipmentForm.reset();
        });
    }




    // click outside to close  forms
    document.addEventListener("click", function (event) {

        if (event.target === equipmentFormContainer) {
            document.body.classList.remove('overflow-hidden');
            equipmentFormContainer.style.display = "none";
            equipmentForm.reset();
        }



        if (event.target === manufacturerFormModal) {

            document.body.classList.remove('overflow-hidden');
            manufacturerFormModal.style.display = "none";
            manufacturerForm.reset();
        }



        if (event.target === modelFormModal) {
            document.body.classList.remove('overflow-hidden');
            modelFormModal.style.display = "none";
            modelForm.reset();
        }
    });


    // edit & delete equipment
    if (editEquipmentButton.length > 0 || deleteEquipmentButton.length > 0) {

        // EDIT EQUIPMENT
        editEquipmentButton.forEach(editButton => {
            const equipmentRow = editButton.closest("tr").children;


            editButton.addEventListener("click", function () {

                // update title and button text
                document.querySelector("#equipment-form-submit").textContent = document.querySelector("#equipment-form-heading").textContent = "Update Equipment";

                //set id
                equipmentId.value = equipmentRow[0].textContent;

                // set model
                const manufacturerId = equipmentRow[3].children[0].textContent;
                const modelDescription = equipmentRow[3].children[2].textContent;
                equipmentModel.value = `${manufacturerId},${modelDescription}`;

                //set location
                const propertyId = equipmentRow[2].children[0].textContent;
                const locationName = equipmentRow[2].children[1].textContent.split("-")[1];
                equipmentLocation.value = `${propertyId},${locationName}`;

                //set serial number
                serialNumber.value = equipmentRow[4].textContent;

                // set manufactured date
                manufacturedDate.value = equipmentRow[5].textContent;

                // show form
                window.scrollTo({ top: 0, behavior: "smooth" });
                document.body.classList.add('overflow-hidden');
                equipmentFormContainer.style.display = "block";
            });
        });



        // DELETE EQUIPMENT
        deleteEquipmentButton.forEach(deleteButton => {
            deleteButton.addEventListener("click", function () {
                const deleteEquipmentIdValue = deleteButton.closest("tr").firstElementChild.textContent;
                const deleteEquipmentIdInput = document.querySelector("#delete-equipment-id");


                statusConfirmationButton.style.display = "none";
                deleteConfirmationButton.style.display = "block";
                confirmationHeading.textContent = "Delete Confirmation";
                confirmationQuestion.textContent = "Please note, this action is irreversible. Are you sure you want to delete this equipment?";

                deleteConfirmationButton.setAttribute("form", "delete-equipment");
                deleteEquipmentIdInput.value = deleteEquipmentIdValue;
                // prevent scrolling
                document.body.classList.add('overflow-hidden');
                window.scrollTo({ top: 0, behavior: "smooth" });
                confirmationWindow.style.display = "block";
            });
        })

    }



    //////////////////////////////////////////////////////////////////////////////////////////////
// Manufacturer


    // open manufacturer form
    if (openManufacturerForm !== null) {
        openManufacturerForm.addEventListener("click", function () {

            document.querySelector("#manufacturer-form-heading").textContent = "Create Manufacturer";
            document.body.classList.add('overflow-hidden');
            window.scrollTo({ top: 0, behavior: "smooth" });
            manufacturerForm.action = "/add-manufacturer"; // Set the form action to create
            manufacturerFormModal.style.display = "block";
        });
    }


    // close manufacturer form with button
    if (manufacturerFormModal !== null) {
        closeManufacturerForm.addEventListener("click", function () {
            document.body.classList.remove('overflow-hidden');
            manufacturerFormModal.style.display = "none";
            manufacturerForm.reset();
        });
    }


    // edit manufacturer
    if (editManufacturerButton.length > 0 && deleteManufacturerButton.length > 0) {
        editManufacturerButton.forEach(editButton => {

            editButton.addEventListener("click", function () {
                manufacturerFormHeading.textContent = manufacturerFormSubmit.textContent = "Update Manufacturer"


                // set id
                manufacturerId.value = this.parentElement.nextElementSibling.textContent;

                // set manufacturer name
                manufacturerName.value = this.parentElement.nextElementSibling.nextElementSibling.textContent;

                // show form
                document.body.classList.add('overflow-hidden');
                window.scrollTo({ top: 0, behavior: "smooth" });
                manufacturerFormModal.style.display = "block";
            });
        });




        // delete manufacturer
        deleteManufacturerButton.forEach(deleteButton => {
            deleteButton.addEventListener("click", function () {

                //set id
                document.querySelector("#delete-manufacturer-id").value = this.parentElement.nextElementSibling.textContent;
                deleteConfirmationButton.setAttribute("form", "delete-manufacturer");

                statusConfirmationButton.style.display = "none";
                deleteConfirmationButton.style.display = "block";
                confirmationHeading.textContent = "Delete Manufacturer";
                confirmationQuestion.textContent = "Please note, this action is irreversible and will fail if related equipment are found. Do you want to delete this manufacturer and all its models?";


                document.body.classList.add('overflow-hidden');
                window.scrollTo({ top: 0, behavior: "smooth" });
                confirmationWindow.style.display = "block";
            });
        });


    }








    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Model


    //open model form
    if (openModelForm) {
        openModelForm.addEventListener("click", function () {
            document.body.classList.add('overflow-hidden');
            window.scrollTo({ top: 0, behavior: "smooth" });
            modelForm.action = "/add-model"; // Set the form action to create
            document.querySelector("#model-form-heading").textContent = "Create Model";
            modelFormModal.style.display = "block";
        });

    }

    //close model form with button
    if (modelFormModal !== null) {
        closeModelForm.addEventListener("click", function () {
            document.body.classList.remove('overflow-hidden');
            modelFormModal.style.display = "none";
            modelForm.reset();
        });
    }


    // Edit  model
    if (editModelButton.length > 0) {
        editModelButton.forEach(editButton => {

            editButton.addEventListener("click", function () {
                const manufacturerValue = this.parentElement.previousElementSibling.previousElementSibling.textContent;
                const descriptionValue = this.parentElement.previousElementSibling.textContent;

                //set modelId
                document.querySelector("#model-id").value = manufacturerValue + "," + descriptionValue;

                // set manufacturer
                document.querySelector("#model-manufacturer").value = manufacturerValue;

                // set model description
                document.querySelector("#model-description").value = descriptionValue;

                // stop scrolling
                document.body.classList.add('overflow-hidden');
                window.scrollTo({ top: 0, behavior: "smooth" });
                // update title and button texts
                document.querySelector("#model-form-submit").textContent = "Edit Model";
                document.querySelector("#model-form-heading").textContent = "Edit Model";
                //show form
                modelFormModal.style.display = "block";
            });
        });
    }


    // delete model
    if (deleteModelButton) {
        deleteModelButton.forEach(deleteButton => {
            deleteButton.addEventListener("click", function () {

                const manufacturerValue = this.parentElement.previousElementSibling.previousElementSibling.textContent;
                const descriptionValue = this.parentElement.previousElementSibling.textContent;

                //set modelId
                document.querySelector("#delete-model-id").value = manufacturerValue + "," + descriptionValue;

                //set texts
                confirmationHeading.textContent = "Delete Model";
                confirmationQuestion.textContent = "This action is permanent. Do you want to delete this model?";

                deleteConfirmationButton.setAttribute("form", "delete-model");

                statusConfirmationButton.style.display = "none";
                deleteConfirmationButton.style.display = "block";


                document.body.classList.add('overflow-hidden');
                window.scrollTo({ top: 0, behavior: "smooth" });
                confirmationWindow.style.display = "block";
            });
        });
    }

});