import calendarjs from "@calendarjs/ce";
import { api } from '../utils/http-util.js'
import slot_response from './mocks/slots-response.json'
import { dateTimeFormatFromEpoch } from "../utils/utils.js";
import { dateTimeFormatFromDate } from "../utils/utils.js";
import { epochFromDateAndTime } from "../utils/utils.js";

console.log('user_main_page.js loaded');

const { Schedule } = calendarjs;
let x;

getSlots()
    .then(arr => {
        x = 'hi'
        Schedule(document.getElementById('root'), {
            type: 'week',
            value: getStartDate(arr),
            weekly: false,
            data: arr,
            grid: 30,
            validRange: ["07:00", "21:00"],
            oncreate: function (self, events) {
                //create slot
                const event = Array.isArray(events) ? events[0] : events;
                postEvent(event)
            },
            onchangeevent: function (self, newValue, oldValue) {
                console.log('Updated:', newValue.title);
            },
            ondelete: function (self, event) {
                console.log('Deleted:', event.title);
            }
        });
    })

    console.log(x)

function getStartDate(arr) {
    let renderStartDate = null;
    arr.forEach(element => {
        if (renderStartDate === null) {
            renderStartDate = new Date(element.startEpoch * 1000);
        } else {
            const current = new Date(element.startEpoch * 1000);
            if (renderStartDate > current) {
                renderStartDate = current;
            }
        }
    });

    console.log(renderStartDate)
    return dateTimeFormatFromDate(renderStartDate)[0];
}


async function fetchSlots() {
    //TODO real request
    //TODO sprosit pochemu kogda siuda dobavliayem async tablica ne otrisovyvayetsia
    console.log("Searching for existing slots to render");
    try {
        const response = await api.get("v1/slots")

        //prodoljat` tut

        console.log("Slots found", response.data);
    } catch (error) {
        console.error("Failed to create slot", error)
    }

    return slot_response;
}

async function getSlots() {
    const slots = await fetchSlots()
    return slots.map(slot => {
        const event = {
            guid: slot.id,
            title: slot.type,
            start: dateTimeFormatFromEpoch(slot.startTime)[1],
            date: dateTimeFormatFromEpoch(slot.startTime)[0],
            end: dateTimeFormatFromEpoch(slot.endTime)[1],
            startEpoch: slot.startTime
        };
        console.log(event);
        return event;
    })

}

async function postEvent(event) {
    console.log("Starting slot creation: ", event);
    try {
        await api.post("v1/slots", {
            trainerId: 1, //TODO 
            type: event.title,
            location: "some_location_1",
            startTime: epochFromDateAndTime(event.date, event.start),
            endTime: epochFromDateAndTime(event.date, event.end),
            duration: epochFromDateAndTime(event.date, event.end) - epochFromDateAndTime(event.date, event.start),
            capacity: 4 //hardcoded for now
        })
        console.log("Slot created, ", event.title, event.start, event.end);
    } catch (error) {
        console.error("Failed to create slot", error)
    }
}


/////////////////////////


document.addEventListener('DOMContentLoaded', () => {
    const toAdminButton = document.getElementById('to-admin-page');
    if (toAdminButton) {
        console.log("HI")
        toAdminButton.addEventListener('click', redirect);
    }
});

const redirect = () => {
    console.log("Trying to go to admin page.")
    window.location.assign("admin.html")
}

const btn1 = document.getElementById('btn-1')
const line1 = document.getElementById('line1')

btn1.addEventListener('click', () => {
    console.log(ret())
    line1.innerHTML = 'SMTH'
})
// console.log(btn1)

function ret() {
    return render => render`aaa`
}