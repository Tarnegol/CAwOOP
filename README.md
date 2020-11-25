# CAwOOP

class Rule:

recieves as input a 3d int array of the form:
[	[[s0, s0], [n0, s0], [n1, s1], ...],
	[[s0, s1], [n0, s0], [n1, s1], ...],
	[[s0, s2], [n0, s0], [n1, s1], ...],
	...
	[[s1, s0], [n0, s0], [n1, s1], ...],
	[[s1, s1], [n0, s0], [n1, s1], ...],
	[[s1, s2], [n0, s0], [n1, s1], ...],
	...
	[[s2, s0], [n0, s0], [n1, s1], ...], ...]

which means: a cell of state (sx) will change it's state to (sy) if it's neighbourhood contains n0 cells of state s0, n1 cells of state s1, etc.
The array only has to contain relevant state changes. In case a cell has multiple possible transformations, the first one in the array will occure.