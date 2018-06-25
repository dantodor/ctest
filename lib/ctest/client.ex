defmodule Ctest.Client do

  def analyze(input, timeout \\ 5000) do
    server = get_java_server()
    GenServer.call(server, {:analyze, input}, timeout)
  end

  defp get_java_server() do
    java_node = "__ctest__" <> Atom.to_string(Kernel.node())
    {:ctest_java_server, String.to_atom(java_node)}
  end

end