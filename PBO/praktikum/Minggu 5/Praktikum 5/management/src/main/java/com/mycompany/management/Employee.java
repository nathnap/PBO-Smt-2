/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.management;

/**
 *
 * @author 7320
 */
public class Employee {

    private String id;
    private String name;
    private Department department;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        if (department.canAddEmployee()) {

            if (this.department != null) {
                this.department.deleteEmployee(this);
            }

            department.addEmployee(this);
            this.department = department;
        }
    }

    public String toString() {
        return id + " " + name + " " + department.getName();
    }
}
