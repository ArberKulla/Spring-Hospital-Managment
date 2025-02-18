    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    const patientId = urlParams.get('patientId');


function setBackLink(){
    const createAdmission = document.getElementById('backToList');
    createAdmission.href=`/admission-state?id=${patientId}`
}

document.getElementById("editAdmissionStateForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const departmentId = document.getElementById("department").value;

    axios.put(`/api/admission-state/updateDepartment/${id}/${departmentId}`)
        .then(response => {
       alert("Department moved successfully!");
       window.location.href = `/admission-state?id=${patientId}`;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while moving the patient.');
        });
});

    window.onload = function() {
        setBackLink();
        loadDepartments();
    };

    function loadDepartments() {
        axios.get('/api/departments')
            .then(response => {
                const departmentSelect = document.getElementById('department');
                departmentSelect.innerHTML = '<option value="">Select a department</option>'; // Reset options

                response.data.forEach(department => {
                    let option = document.createElement('option');
                    option.value = department.id;
                    option.textContent = department.name;
                    departmentSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching departments:', error));
    }
