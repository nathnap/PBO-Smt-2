/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.management;

/**
 *
 * @author 7320
 */
public class Department {

    private String id;
    private String name;
    private int maxNum;
    private Employee[] employeeList;

    public Department(String id, String name, int maxNum) {
        this.id = id;
        this.name = name;
        this.maxNum = maxNum;
        employeeList = new Employee[maxNum];
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public int getEmployeeCount() {
        int count = 0;

        for (int i = 0; i < employeeList.length; i++) {
            if (employeeList[i] != null) {
                count++;
            }
        }
        return count;
    }

    public boolean canAddEmployee() {
        return getEmployeeCount() < maxNum;
    }

    public void addEmployee(Employee employee) {
        for (int i = 0; i < employeeList.length; i++) {

            if (employeeList[i] == null) {
                employeeList[i] = employee;
                break;
            }
        }
    }

    public void deleteEmployee(Employee employee) {
        for (int i = 0; i < employeeList.length; i++) {

            if (employeeList[i] != null &&
                employeeList[i].getId().equals(employee.getId())) {

                employeeList[i] = null;
            }
        }
    }

    public void printEmployeeList() {
        System.out.println("Anggota " + name + " (Total " + getEmployeeCount() + " Orang)");

        for (int i = 0; i < employeeList.length; i++) {

            if (employeeList[i] != null) {
                System.out.println((i + 1) + " " + employeeList[i]);
            }
        }

        System.out.println();
    }
}
