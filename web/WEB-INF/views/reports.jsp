<!DOCTYPE html>
<html>

<%@ include file="includes/Header.jsp" %>

<body>

<%@ include file="includes/navbar.jsp" %>

<div class="container">

    <h1 style="margin-top:20px;">Reports</h1>

    <hr>

    <table border="1" cellpadding="15" cellspacing="0" width="100%">

        <tr style="background:#e5e7eb;">
            <th>Report</th>
            <th>Description</th>
            <th>Open</th>
        </tr>

        <tr>
            <td><strong>Inventory Report</strong></td>
            <td>Shows all cleaning materials currently stored.</td>
            <td>
                <a href="${pageContext.request.contextPath}/MaterialServlet?action=list" class="btn">
                    View
                </a>
            </td>
        </tr>

        <tr>
            <td><strong>Low Stock Report</strong></td>
            <td>Displays materials below their reorder level.</td>
            <td>
                <a href="${pageContext.request.contextPath}/MaterialServlet?action=lowstock" class="btn">
                    View
                </a>
            </td>
        </tr>

        <tr>
            <td><strong>Supplier Report</strong></td>
            <td>Displays all registered suppliers.</td>
            <td>
                <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="btn">
                    View
                </a>
            </td>
        </tr>

        <tr>
            <td><strong>Cleaner Report</strong></td>
            <td>Displays all registered cleaners.</td>
            <td>
                <a href="${pageContext.request.contextPath}/CleanerServlet?action=list" class="btn">
                    View
                </a>
            </td>
        </tr>

        <tr>
            <td><strong>Stock Issuance History</strong></td>
            <td>Displays every stock issue transaction.</td>
            <td>
                <a href="${pageContext.request.contextPath}/IssuanceServlet?action=list" class="btn">
                    View
                </a>
            </td>
        </tr>

    </table>

    <br><br>

    <h2>System Summary</h2>

    <table border="1" cellpadding="10" cellspacing="0" style="margin-bottom: 20px;">

        <tr>
            <th style="background:transparent;">Total Materials</th>
            <td>${totalMaterials}</td>
        </tr>

        <tr>
            <th style="background:transparent;">Total Suppliers</th>
            <td>${totalSuppliers}</td>
        </tr>

        <tr>
            <th style="background:transparent;">Total Cleaners</th>
            <td>${totalCleaners}</td>
        </tr>

        <tr>
            <th style="background:transparent;">Low Stock Items</th>
            <td>${lowStock}</td>
        </tr>

    </table>

    <br>

    <button class="btn" onclick="window.print();">
        Print Report
    </button>

</div>

<%@ include file="includes/footer.jsp" %>

</body>
</html>