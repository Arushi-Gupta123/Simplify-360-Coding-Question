import java.util.*;

public class ProjectScheduler {

    static class Task {
        String id;
        int duration;
        int est; // Earliest Start Time
        int eft; // Earliest Finish Time
        int lst; // Latest Start Time
        int lft; // Latest Finish Time
        List<String> dependencies; // Dependencies for this task

        Task(String id, int duration, List<String> dependencies) {
            this.id = id;
            this.duration = duration;
            this.est = 0; // Earliest start time for the starting task
            this.eft = 0; // To be computed
            this.lst = Integer.MAX_VALUE; // To be computed
            this.lft = Integer.MAX_VALUE; // To be computed
            this.dependencies = dependencies;
        }
    }

    private Map<String, Task> tasks = new HashMap<>();
    private Map<String, Set<String>> graph = new HashMap<>();
    private Map<String, Integer> inDegree = new HashMap<>();

    // Add a task with its dependencies
    public void addTask(String id, int duration, List<String> dependencies) {
        Task task = new Task(id, duration, dependencies);
        tasks.put(id, task);
        inDegree.put(id, 0);
        for (String dep : dependencies) {
            graph.computeIfAbsent(dep, k -> new HashSet<>()).add(id);
            inDegree.put(id, inDegree.getOrDefault(id, 0) + 1);
        }
    }

    // Calculate the Earliest Finish Times
    public void calculateEarliestFinishTimes() {
        Queue<String> queue = new LinkedList<>();
        for (String taskId : tasks.keySet()) {
            if (inDegree.get(taskId) == 0) {
                queue.add(taskId);
                Task task = tasks.get(taskId);
                task.eft = task.duration;
            }
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            Task currentTask = tasks.get(current);
            for (String neighbor : graph.getOrDefault(current, new HashSet<>())) {
                Task neighborTask = tasks.get(neighbor);
                neighborTask.est = Math.max(neighborTask.est, currentTask.eft);
                neighborTask.eft = neighborTask.est + neighborTask.duration;
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }
    }

    // Calculate the Latest Finish Times
    public void calculateLatestFinishTimes() {
        int maxEFT = tasks.values().stream().mapToInt(task -> task.eft).max().orElse(0);

        for (Task task : tasks.values()) {
            task.lft = maxEFT;
            task.lst = task.lft - task.duration;
        }

        Queue<String> queue = new LinkedList<>();
        for (String taskId : tasks.keySet()) {
            if (graph.getOrDefault(taskId, new HashSet<>()).isEmpty()) {
                queue.add(taskId);
            }
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            Task currentTask = tasks.get(current);
            for (Map.Entry<String, Set<String>> entry : graph.entrySet()) {
                String dep = entry.getKey();
                if (entry.getValue().contains(current)) {
                    Task depTask = tasks.get(dep);
                    depTask.lft = Math.min(depTask.lft, currentTask.lst);
                    depTask.lst = depTask.lft - depTask.duration;
                    if (graph.getOrDefault(dep, new HashSet<>()).stream().allMatch(neighbor -> tasks.get(neighbor).lft < Integer.MAX_VALUE)) {
                        queue.add(dep);
                    }
                }
            }
        }
    }

    // Print the earliest and latest completion times
    public void printCompletionTimes() {
        int earliestCompletion = tasks.values().stream().mapToInt(task -> task.eft).max().orElse(0);
        int latestCompletion = tasks.values().stream().mapToInt(task -> task.lft).max().orElse(0);
        System.out.println("Earliest time all tasks will be completed: " + earliestCompletion);
        System.out.println("Latest time all tasks will be completed: " + latestCompletion);
    }

    public static void main(String[] args) {
        ProjectScheduler scheduler = new ProjectScheduler();
        scheduler.addTask("T1", 5, Collections.emptyList());
        scheduler.addTask("T2", 3, Collections.singletonList("T1"));
        scheduler.addTask("T3", 2, Collections.singletonList("T1"));
        scheduler.addTask("T4", 4, Arrays.asList("T2", "T3"));

        scheduler.calculateEarliestFinishTimes();
        scheduler.calculateLatestFinishTimes();
        scheduler.printCompletionTimes();
    }
}

/*
Time Complexity:
Topological Sort: O(V + E)
Calculating EFT and LFT: O(V + E) (each node and edge processed once)

Space Complexity:
O(V + E) (for graph representation and traversal structures)

 */