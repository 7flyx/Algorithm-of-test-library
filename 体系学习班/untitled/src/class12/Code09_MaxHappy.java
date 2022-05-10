package class12;

import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-10
 * Time: 16:28
 * Description: 员工的最大快乐值
 */
public class Code09_MaxHappy {
    private static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int happy, List<Employee> nexts) {
            this.happy = happy;
            this.nexts = nexts;
        }
    }

    private static class Info {
        public int yes;
        public int no;

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }


    public static int getEmployeeHappy(Employee employee) {
        if (employee == null) {
            return 0;
        }
        Info allInfo = process(employee);
        return Math.max(allInfo.no, allInfo.yes);
    }

    private static Info process(Employee employee) {
        if (employee == null) {
            return new Info(0, 0);
        }
        int no = 0;
        int yes = employee.happy;
        for (Employee next : employee.nexts) {
            Info nextInfo = process(next);
            no += Math.max(nextInfo.no, nextInfo.yes); // 当前位置的数据不要，下一个位置的数据我可要可不要
            yes += nextInfo.no;
        }
        return new Info(yes, no);
    }
}
