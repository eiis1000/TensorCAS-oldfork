package config;

import functions.GeneralFunction;
import functions.unitary.transforms.Transformation;
import tools.MiscTools;
import tools.ParsingTools;

import java.io.IOException;

/**
 * The {@link Settings} class stores global settings as static variables. These settings are read from {@code cas.properties} by {@link SettingsParser#parseConfig()} on launch, and can be modified dynamically during runtime.
 * Settings that are modified during runtime do NOT get written into the properties file. To save a settings profile, write it into {@code cas.properties} so it is read on launch.
 */
@SuppressWarnings("CanBeFinal")
public class Settings {

	/**
	 * When this setting is enabled, {@link Settings} uses {@link SettingsParser} to read {@code cas.properties} and those values are stored in this class.
	 */
	public static boolean readProperties = true;

	static {
		//noinspection ConstantConditions
		if (readProperties) {
			try {
				SettingsParser.parseConfig();
			} catch (IOException e) {
				System.out.println("Properties file not found. Using defaults...");
			}
		}
	}

	private Settings(){}

	/**
	 * The amount of times that {@link tools.singlevariable.Solver} will run unless the exit conditions are met beforehand
	 */
	public static int defaultSolverIterations;

	/**
	 * The amount of segments which {@link tools.singlevariable.Solver} splits a given range into
	 */
	public static int defaultRangeSections;

	/**
	 * The number of segments that {@link tools.singlevariable.NumericalIntegration#simpsonsRule(GeneralFunction, double, double)} uses when performing numerical integration. **MUST BE EVEN**
	 */
	public static int simpsonsSegments;

	/**
	 * The variable to be used in {@link tools.singlevariable} when none is specified
	 */
	public static char singleVariableDefault;

	/**
	 * The margin that {@link tools.singlevariable.Solver} uses to determine if a value is close enough to zero to be considered a root of a function
	 */
	public static double zeroMargin;

	/**
	 * The margin used in {@link ParsingTools#toInteger(double)} when deciding when a {@code double} is close enough to an integer
	 */
	public static double integerMargin;

	/**
	 * The default margin to be used when checking if two doubles are equal
	 */
	public static double equalsMargin;

	/**
	 * The default amount to be slept on newlines in {@link MiscTools#printWithSleep}
	 */
	public static double defaultSleep;

	/**
	 * Denotes whether or not expressions like {@code sin(pi/2)} must be escaped to {@code \sin(\pi/2)}. Enabling this is strongly recommended, and may reduce bugs.
	 */
	public static boolean enforceEscapes;

	/**
	 * Denotes whether functions of constants should be simplified. Ex: {@code \sin(\pi/2) -> 1}
	 */
	public static boolean simplifyFunctionsOfConstants;

	/**
	 * Denotes whether exponents should be distributed over multiplication in {@code simplify()}. Ex: {@code (2x)^2 -> 4x^2}
	 */
	public static boolean distributeExponents;

	/**
	 * Denotes whether or not the derivatives of functions should be cached when created
	 */
	public static boolean cacheDerivatives;

	/**
	 * Forces functions in {@link functions.unitary.combo} to return integers when using approximations
	 */
	public static boolean enforceIntegerOperations;

	/**
	 * Denotes whether methods in {@link tools.singlevariable} should exit if the result is within a certain proximity of the target. Improves performance at the cost of accuracy.
	 */
	public static boolean exitSolverOnProximity;

	/**
	 * Denotes whether a {@link Transformation} should execute its action when {@code simplify()} is called
	 */
	public static boolean executeOnSimplify;

	/**
	 * Denotes whether products are distributed over addition in {@code simplify()}. Ex: {@code x(y+z) -> (xy+xz)}
	 */
	public static boolean distributeFunctions;

	/**
	 * Denotes whether full stack traces of errors should be printed for debugging
	 */
	public static boolean printStackTraces;

	/**
	 * Denotes the default method used to solve equations
	 */
	public static SolverType defaultSolverType;

	/**
	 * Denotes the default implementation of {@link functions.unitary.combo.Factorial} to be used
	 */
	public static FactorialType defaultFactorial;
}
