defmodule Ctest do
  use Application


  def start(_type, _args) do
    import Supervisor.Spec, warn: false

    children = [
      worker(Ctest.ScalaServer, [Application.get_env(:ctest, :zk)])
    ]

    opts = [strategy: :one_for_one, name: Ctest.Supervisor]
    Supervisor.start_link(children, opts)
    end

end
