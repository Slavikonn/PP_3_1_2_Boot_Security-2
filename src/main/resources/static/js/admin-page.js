function loadUsers() {
    const tbody = document.querySelector("#multiUserTable tbody");
    if (!tbody) return;

    fetch("/api/admin/users")
        .then(res => res.json())
        .then(users => {
            tbody.innerHTML = "";
            users.forEach(user => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(r => r.replace("ROLE_", "")).join(", ")}</td>
                    <td><button class="btn btn-info btn-sm" onclick="openEditModal(${user.id})">Edit</button></td>
                    <td><button class="btn btn-danger btn-sm" onclick="openDeleteModal(${user.id})">Delete</button></td>
                `;
                tbody.appendChild(row);
            });
        });
}
