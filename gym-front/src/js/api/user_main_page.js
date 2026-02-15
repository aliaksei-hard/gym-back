import calendarjs from "@calendarjs/ce";
import { api } from '../utils/http-util.js'
import slot_response from './mocks/slots-response.json'


console.log('user_main_page.js loaded');

const { Schedule } = calendarjs;

const arr = getSlots();

Schedule(document.getElementById('root'), {
    type: 'week',
    value: getStartDate(arr),
    weekly: false,
    data: arr,
    oncreate: function (self, events) {
        fetchExistingSlots();
        const event = Array.isArray(events) ? events[0] : events;
        console.log('Created:', event.title);
    },
    onchangeevent: function (self, newValue, oldValue) {
        console.log('Updated:', newValue.title);
    },
    ondelete: function (self, event) {
        console.log('Deleted:', event.title);
    }
});

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


function fetchSlots() {
    return slot_response;
}

function getSlots() {
    const slots = fetchSlots()
    return slots.map(slot => {
         const event = {  // Use plain object literal, not "new Event()"
            guid: slot.id,
            title: slot.type,
            start: dateTimeFormat(slot.startTime)[1],
            date: dateTimeFormat(slot.startTime)[0],
            end: dateTimeFormat(slot.endTime)[1],
            startEpoch: slot.startTime
        };
        console.log(event);
        return event;
    })

}

function dateTimeFormat(epoch) {
    const entire = new Date(epoch * 1000);
    const iso = entire.toISOString();
    const time = iso.split('T')[1].slice(0,5);
    const date = iso.split('T')[0];
    
    return [date, time];
}

function dateTimeFormatFromDate(raw) {
    const iso = raw.toISOString();
    const time = iso.split('T')[1].slice(0,5);
    const date = iso.split('T')[0];
    
    return [date, time];
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