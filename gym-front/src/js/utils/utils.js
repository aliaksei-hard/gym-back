
export function dateTimeFormatFromEpoch(epoch) {
    const entire = new Date(epoch * 1000);
    const iso = entire.toISOString();
    const time = iso.split('T')[1].slice(0, 5);
    const date = iso.split('T')[0];

    return [date, time];
}

export function dateTimeFormatFromDate(raw) {
    const iso = raw.toISOString();
    const time = iso.split('T')[1].slice(0, 5);
    const date = iso.split('T')[0];

    return [date, time];
}

export function epochFromDateAndTime(date, time) {
    const dateTime = date + 'T' + time + ':00'
    return Math.floor(new Date(dateTime).getTime() / 1000);
}