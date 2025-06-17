



// buttons
const manageUserButton = document.querySelector("#manage-user-btn");
const manageEquipmentButton = document.querySelector("#manage-equipment-btn");
const managePropertyButton = document.querySelector("#manage-property-btn");
const manageReportButton = document.querySelector("#manage-report-btn");

// screens
const manageUserScreen = document.querySelector("#manage-user-screen");
const manageEquipmentScreen = document.querySelector("#manage-equipment-screen");
const managePropertyScreen = document.querySelector("#manage-property-screen");
const manageReportScreen = document.querySelector("#manage-report-screen");


// handle refresh to same screen
const screenMap = {
    "manage-user-screen": {
        screen: manageUserScreen,
        button: manageUserButton
    },
    "manage-equipment-screen": {
        screen: manageEquipmentScreen,
        button: manageEquipmentButton
    },
    "manage-property-screen": {
        screen: managePropertyScreen,
        button: managePropertyButton
    },
    "manage-report-screen": {
        screen: manageReportScreen,
        button: manageReportButton
    }
};

const defaultScreen = "manage-equipment-screen";
const activeScreenId = localStorage.getItem("activeScreen") || defaultScreen;

Object.keys(screenMap).forEach(id => {
    screenMap[id].screen.classList.add("hidden");
    screenMap[id].button.classList.remove("border-2", "border-color-4",  "text-color-4");
});

const active = screenMap[activeScreenId];
active.screen.classList.remove("hidden");
active.button.classList.add("border-2", "border-color-4",  "text-color-4");




// highlight nav buttona and show screen on reload
if (!manageEquipmentButton.classList.contains("border-color-4")
    && !manageUserButton.classList.contains("border-color-4")
    && !managePropertyButton.classList.contains("border-color-4")
    && !manageReportButton.classList.contains("border-color-4")) {

    manageEquipmentButton.classList.add("border-2", "border-color-4",  "text-color-4");
    manageEquipmentScreen.classList.remove("hidden");
}



// show user management screen
if (manageUserButton !== null) {
    manageUserButton.addEventListener("click", function () {
        localStorage.setItem("activeScreen", "manage-user-screen");

        manageUserScreen.classList.remove("hidden");
        manageEquipmentScreen.classList.add("hidden");
        managePropertyScreen.classList.add("hidden");
        manageReportScreen.classList.add("hidden");
        manageUserButton.classList.add("border-2", "border-color-4",  "text-color-4");
        manageEquipmentButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        managePropertyButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageReportButton.classList.remove("border-2", "border-color-4",  "text-color-4");
    });
}

// show equipment management screen
if (manageEquipmentButton !== null) {
    manageEquipmentButton.addEventListener("click", function () {
        localStorage.setItem("activeScreen", "manage-equipment-screen");

        manageEquipmentScreen.classList.remove("hidden");
        manageUserScreen.classList.add("hidden");
        managePropertyScreen.classList.add("hidden");
        manageReportScreen.classList.add("hidden");
        manageUserButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageEquipmentButton.classList.add("border-2", "border-color-4",  "text-color-4");
        managePropertyButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageReportButton.classList.remove("border-2", "border-color-4",  "text-color-4");
    });
}

//show property management screen
if (managePropertyButton !== null) {
    managePropertyButton.addEventListener("click", function () {
        localStorage.setItem("activeScreen", "manage-property-screen");

        manageUserScreen.classList.add("hidden");
        manageEquipmentScreen.classList.add("hidden");
        managePropertyScreen.classList.remove("hidden");
        manageReportScreen.classList.add("hidden");
        manageUserButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageEquipmentButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        managePropertyButton.classList.add("border-2", "border-color-4",  "text-color-4");
        manageReportButton.classList.remove("border-2", "border-color-4",  "text-color-4");
    });
}

//show reporting screen
if (manageReportButton !== null) {
    manageReportButton.addEventListener("click", function () {
        localStorage.setItem("activeScreen", "manage-report-screen");

        manageUserScreen.classList.add("hidden");
        manageEquipmentScreen.classList.add("hidden");
        managePropertyScreen.classList.add("hidden");
        manageReportScreen.classList.remove("hidden");
        manageUserButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageEquipmentButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        managePropertyButton.classList.remove("border-2", "border-color-4",  "text-color-4");
        manageReportButton.classList.add("border-2", "border-color-4",  "text-color-4");
    });
}