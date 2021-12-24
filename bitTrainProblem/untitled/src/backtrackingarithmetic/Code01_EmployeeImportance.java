package backtrackingarithmetic;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:07
 * Description: 员工的重要性
 * 给定一个保存员工信息的数据结构，它包含了员工 唯一的 id ，重要度 和 直系下属的 id 。
 * 比如，员工 1 是员工 2 的领导，员工 2 是员工 3 的领导。他们相应的重要度为 15 , 10 , 5 。
 * 那么员工 1 的数据结构是 [1, 15, [2]] ，员工 2的 数据结构是 [2, 10, [3]] ，员工 3 的数据结构是 [3, 5, []] 。
 * 注意虽然员工 3 也是员工 1 的一个下属，但是由于 并不是直系 下属，因此没有体现在员工 1 的数据结构中。
 * 现在输入一个公司的所有员工信息，以及单个员工 id ，返回这个员工和他所有下属的重要度之和。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/employee-importance
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
}

public class Code01_EmployeeImportance {
    public int getImportance(List<Employee> employees, int id) {
        if (employees == null) {
            return 0;
        }

        // Employee cur = findEmp(employees, id);//找到根节点
        // return process(cur, employees);
        return process(employees, id);
    }

    private Employee findEmp(List<Employee> employees, int id) {
        Employee cur = null;
        for (Employee emp : employees) {
            if (id == emp.id) {
                cur = emp;
                break;
            }
        }
        return cur;
    }

    //解法一-深度搜索
    private int process(Employee emp, List<Employee> employees) {
        int sum = emp.importance;
        for (int i : emp.subordinates) {
            Employee next = findEmp(employees, i);
            sum += process(next, employees);
        }
        return sum;
    }

    //解法二-广度搜索
    Map<Integer, Employee> map = new HashMap<>(); //存储每个id所对应的对象
    private int process(List<Employee> employees, int id) {
        for (Employee emp : employees) {
            map.put(emp.id, emp);
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(id);
        int sum = 0;
        while (!queue.isEmpty()) {
            id = queue.poll();
            Employee emp = map.get(id);
            sum += emp.importance;
            List<Integer> list = emp.subordinates;
            for (Integer i : list) {
                queue.add(i);
            }
        }
        return sum;
    }


}