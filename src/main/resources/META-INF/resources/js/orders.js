function updateSelectedButton(btn) {
    var btnAllJQ = PF('allBtn').getJQ();
    var btnPaidJQ = PF('paidSuccessBtn').getJQ();
    var btnNotPaidJQ = PF('notPaidBtn').getJQ();
    var spanHtml = '<span class="ui-button-icon-left ui-icon ui-c ui-icon-grip-solid-vertical"></span>';
    var parent = btnAllJQ.parent();
    console.info('btn number ', parent.find("button").length);
    switch (btn) {
        //  可以继续提炼精简
        case 0:
            var children = btnAllJQ.children('.ui-icon-grip-solid-vertical');
            if (children.length <= 0) {
                btnAllJQ.append(spanHtml);
            }
            var childrenPaid = btnPaidJQ.children('.ui-icon-grip-solid-vertical');
            if (children.length > 0) {
                btnPaidJQ.remove(childrenPaid);
            }
            var childrenNotPaid = btnNotPaidJQ.children('.ui-icon-grip-solid-vertical');
            if (children.length > 0) {
                btnNotPaidJQ.remove(childrenNotPaid);
            }

            break;
        case 1:
            var children = btnAllJQ.children('.ui-icon-grip-solid-vertical');
            if (children.length > 0) {
                btnAllJQ.remove(children);
            }
            var childrenNotPaid = btnNotPaidJQ.children('.ui-icon-grip-solid-vertical');
            if (children.length > 0) {
                btnNotPaidJQ.remove(childrenNotPaid);
            }

            var childrenPaid = btnPaidJQ.children('.ui-icon-grip-solid-vertical');
            if (children.length <= 0) {
                btnPaidJQ.prepend(spanHtml);
            }

            break;
        case 2:
            var children = btnAllJQ.children('.ui-icon-grip-solid-vertical');
            if (children.length > 0) {
                btnAllJQ.remove(children);
            }
            var childrenPaid = btnPaidJQ.children('.ui-icon-grip-solid-vertical');
            if (children.length > 0) {
                btnNotPaidJQ.remove(childrenPaid);
            }
            var childrenNotPaid = btnNotPaidJQ.children('.ui-icon-grip-solid-vertical');
            if (children.length <= 0) {
                btnNotPaidJQ.prepend(spanHtml);
            }

            break;
    }

}
