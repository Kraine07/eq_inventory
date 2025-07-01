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

                // set model
                equipmentModel.value = equipmentRow[4].children[1].textContent;

                //set location
                equipmentLocation.value = equipmentRow[3].textContent;

                //set serial number
                serialNumber.value = equipmentRow[5].textContent;

                // set manufactured date
                manufacturedDate.value = equipmentRow[6].textContent;

                // show form
                window.scrollTo({ top: 0, behavior: "smooth" });
                document.body.classList.add('overflow-hidden');
                equipmentFormContainer.style.display = "block";
            });
        });

        // DELETE EQUIPMENT

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
    if (editManufacturerButton.length > 0) {
        editManufacturerButton.forEach(editButton => {

            editButton.addEventListener("click", function () {

                // set id
                document.querySelector("#manufacturer-id").value = editButton.parentElement.nextElementSibling.textContent;

                // set manufacturer name
                document.querySelector("#manufacturer-name").value = editButton.parentElement.nextElementSibling.nextElementSibling.textContent;

                // show form
                document.body.classList.add('overflow-hidden');
                window.scrollTo({ top: 0, behavior: "smooth" });
                manufacturerForm.action = "/edit-manufacturer"; // Set the form action to edit
                document.querySelector("#manufacturer-form-submit").textContent = "Edit Manufacturer";
                document.querySelector("#manufacturer-form-heading").textContent = "Edit Manufacturer";
                manufacturerFormModal.style.display = "block";
            });
        });
    }



    // delete manufacturer






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

                // set id
                document.querySelector("#model-id").value = editButton.parentElement.previousElementSibling.previousElementSibling.previousElementSibling.textContent;

                // set manufacturer
                document.querySelector("#model-manufacturer").value = editButton.parentElement.previousElementSibling.previousElementSibling.textContent;

                // set model description
                document.querySelector("#model-description").value = editButton.parentElement.previousElementSibling.textContent;

                // show form
                document.body.classList.add('overflow-hidden');
                window.scrollTo({ top: 0, behavior: "smooth" });
                modelForm.action = "/edit-model"; // Set the form action to edit
                document.querySelector("#model-form-submit").textContent = "Edit Model";
                document.querySelector("#model-form-heading").textContent = "Edit Model";
                modelFormModal.style.display = "block";
            });
        });
    }


    // delete model

});