


//
const equipmentForm = document.getElementById("equipment-form");
const equipmentFormContainer = document.getElementById("equipment-form-container");
const openEquipmentForm = document.getElementById("open-equipment-form-btn");
const closeEquipmentForm = document.querySelector("#close-equipment-form");


//edit & delete equipment
const editEquipmentButton = document.querySelectorAll(".edit-equipment");
const deleteEquipmentButton = document.querySelectorAll(".delete-equipment");

const equipmentId = document.querySelector("#equipment-id");
const equipmentModel = document.querySelector("#equipment-model");
const equipmentLocation = document.querySelector("#equipment-location");
const serialNumber = document.querySelector("#serial-number");
const manufacturedDate = document.querySelector("#manufactured-date");


// manufacturer management
const manageManufacturerButton = document.querySelector("#manufacturer-management-btn");
const manageManufacturerScreen = document.querySelector("#manufacturer-model-management-modal");
const closeManufacturerManagementButton = document.querySelector("#close-manufacturer-model-management-btn");
const manufacturerForm = document.querySelector("#manufacturer-form-modal");
const closeManufacturerFormButton = document.querySelector("#close-manufacturer-form-btn");
const openManufacturerFormButton = document.querySelector("#open-manufacturer-form-btn");

// model management
const manageModelButton = document.querySelector("#model-management-btn");
const closeModelManagementButton = document.querySelector("#close-model-management-btn");
const modelForm = document.querySelector("#model-form-modal");
const closeModelFormButton = document.querySelector("#close-model-form-btn");
const openModelFormButton = document.querySelector("#open-model-form-btn");

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


// click outside to close  form
window.onclick = function (event) {
    if (event.target === equipmentFormContainer) {
        document.body.classList.remove('overflow-hidden');
        equipmentFormContainer.style.display = "none";
        equipmentForm.reset();
    }

    if (event.target === manageManufacturerScreen) {
        document.body.classList.remove('overflow-hidden');
        manageManufacturerScreen.style.display = "none";
    }

    if (event.target === manufacturerForm) {
        document.body.classList.remove('overflow-hidden');
        manufacturerForm.style.display = "none";
        manufacturerForm.reset();
    }

    if (event.target === modelForm) {
        document.body.classList.remove('overflow-hidden');
        modelForm.style.display = "none";
        modelForm.reset();
    }
};


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
//MANUFACTURER MANAGEMENT
// close manufacturer management screen
if (closeManufacturerManagementButton !== null) {
    closeManufacturerManagementButton.addEventListener("click", function () {
        document.body.classList.remove('overflow-hidden');
        manageManufacturerScreen.style.display = "none";
    });
}

// open manufacturer management
if (manageManufacturerButton !== null) {
    manageManufacturerButton.addEventListener("click", function () {
        document.body.classList.add('overflow-hidden');

        manageManufacturerScreen.style.display = "block";
    });
}


//open manufacturer form
if (openManufacturerFormButton !== null) {
    openManufacturerFormButton.addEventListener("click", function () {
        document.body.classList.add('overflow-hidden');
        manageManufacturerScreen.style.display = "none";
        manufacturerForm.style.display = "block";
    });
}


//close manufacturer form
if (closeManufacturerFormButton !== null) {
    closeManufacturerFormButton.addEventListener("click", function () {
        document.body.classList.remove('overflow-hidden');
        manufacturerForm.style.display = "none";
        location.reload();
    });
}



////////////////////////////////////////////////////////////////////////////////////////////////////
// MODEL MANAGEMENT

// open model form
if (openModelFormButton !== null) {
    openModelFormButton.addEventListener("click", function () {
        document.body.classList.add('overflow-hidden');
        manageManufacturerScreen.style.display = "none";
        modelForm.style.display = "block";
    });
}

// close model form
if (closeModelFormButton !== null) {
    closeModelFormButton.addEventListener("click", function () {
        document.body.classList.remove('overflow-hidden');
        modelForm.style.display = "none";
        location.reload();
    });
}
