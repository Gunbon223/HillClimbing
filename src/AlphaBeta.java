public class AlphaBeta {
    public static void main(String[] args) {
        Minimax minimax = new Minimax();
        Minimax.Node node = new Minimax.Node();
        int result = minimax.minimax(node, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        System.out.println(result);
    }
    //code graph for code below


    public static class Minimax {
        public int minimax(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {
            if (depth == 0 || node.isTerminalNode()) {
                return node.evaluate();
            }

            if (maximizingPlayer) {
                int maxEva = Integer.MIN_VALUE;
                for (Node child : node.getChildren()) {
                    int eva = minimax(child, depth - 1, alpha, beta, false);
                    maxEva = Math.max(maxEva, eva);
                    alpha = Math.max(alpha, maxEva);
                    if (beta <= alpha) {
                        break;
                    }
                }
                return maxEva;
            } else {
                int minEva = Integer.MAX_VALUE;
                for (Node child : node.getChildren()) {
                    int eva = minimax(child, depth - 1, alpha, beta, true);
                    minEva = Math.min(minEva, eva);
                    beta = Math.min(beta, eva);
                    if (beta <= alpha) {
                        break;
                    }
                }
                return minEva;
            }
        }

        static class Node {



            public boolean isTerminalNode() {
                // Implement according to your requirements
                return false;
            }

            public int evaluate() {
                // Implement according to your requirements
                return 0;
            }

            public Node[] getChildren() {
                // Implement according to your requirements
                return new Node[0];
            }
        }
    }



}
