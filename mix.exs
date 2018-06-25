defmodule Ctest.Mixfile do
  use Mix.Project

  def project do
    [
      app: :ctest,
      version: "0.1.0",
      elixir: "~> 1.5",
      start_permanent: Mix.env == :prod,
      deps: deps()
    ]
  end

  # Run "mix help compile.app" to learn about applications.
#  def application do
#    [
#      extra_applications: [:logger],
#      mod: {Ctest.Application, []}
#    ]
#  end
  def application do
    [applications: [:logger],
      env: [
        jvm_args: ["-Xms256m", "-Xmx2G"]],
      mod: {Ctest, []}]
  end
  # Run "mix help deps" to learn about dependencies.
  defp deps do
    [
      {:earmark, "~> 1.0", only: :dev},
        {:ex_doc, "~> 0.13.0", only: :dev}
    ]
  end
end
