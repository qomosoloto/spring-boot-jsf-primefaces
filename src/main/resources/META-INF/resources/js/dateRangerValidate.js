/**
 * Faces Validator
 */
PrimeFaces.validator['dateRangeValidator'] = {

    validate: function (element, value) {
        var date = PF('dateStart').getDate();
        var dateEnd = PF('dateEnd').getDate();
        var startDate = new Date(date.getFullYear(), date.getMonth(), date.getDay());
        var endDate = new Date(dateEnd.getFullYear(), date.getMonth(), date.getDay());
        console.info("startDate", startDate);
        console.info("endDate", endDate);

        if (!compareDate(startDate, endDate)) {
            PF('msgs').renderMessage({
                "summary": "Validate Error!",
                "detail": "结束日期必须大于起始日期",
                "severity": "warn"
            });
        }
    }
};

function compareDate(dateStart, dateEnd) {
    return dateStart.getTime() &lt; dateEnd.getTime();
}
/**
 * Created by qomo on 15-11-1.
 */
